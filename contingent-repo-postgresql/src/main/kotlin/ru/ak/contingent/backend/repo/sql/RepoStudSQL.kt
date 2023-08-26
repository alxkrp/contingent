package ru.ak.contingent.backend.repo.sql

import com.benasher44.uuid.uuid4
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ak.contingent.common.helpers.asContError
import ru.ak.contingent.common.models.ContStudent
import ru.ak.contingent.common.models.ContStudentId
import ru.ak.contingent.common.models.ContStudentLock
import ru.ak.contingent.common.models.ContStudentSex
import ru.ak.contingent.common.repo.*
import kotlin.random.Random

class RepoStudSQL(
    properties: SqlProperties,
    initObjects: Collection<ContStudent> = emptyList(),
    val randomUuid: () -> String = { uuid4().toString() },
    val ranfomInt: () ->Int = { Random.nextInt(1, 1000000)},
) : IStudRepository {

    init {
        val driver = when {
            properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
            else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
        }

        Database.connect(
            properties.url, driver, properties.user, properties.password
        )

        transaction {
            if (properties.dropDatabase) SchemaUtils.drop(StudentTable)
            SchemaUtils.create(StudentTable)
            initObjects.forEach { createStud(it) }
        }
    }

    private fun createStud(ad: ContStudent): ContStudent {
        val res = StudentTable.insert {
            to(it, ad, ranfomInt, randomUuid)
        }

        return StudentTable.from(res)
    }

    private fun <T> transactionWrapper(block: () -> T, handle: (Exception) -> T): T =
        try {
            transaction {
                block()
            }
        } catch (e: Exception) {
            handle(e)
        }

    private fun transactionWrapper(block: () -> DbStudResponse): DbStudResponse =
        transactionWrapper(block) { DbStudResponse.error(it.asContError()) }

    override suspend fun createStud(rq: DbStudRequest): DbStudResponse = transactionWrapper {
        DbStudResponse.success(createStud(rq.stud))
    }

    private fun read(id: ContStudentId): DbStudResponse {
        val res = StudentTable.select {
            StudentTable.id eq id.asInt()
        }.singleOrNull() ?: return DbStudResponse.errorNotFound
        return DbStudResponse.success(StudentTable.from(res))
    }

    override suspend fun readStud(rq: DbStudIdRequest): DbStudResponse = transactionWrapper { read(rq.id) }

    private fun update(
        id: ContStudentId,
        lock: ContStudentLock,
        block: (ContStudent) -> DbStudResponse
    ): DbStudResponse =
        transactionWrapper {
            if (id == ContStudentId.NONE) return@transactionWrapper DbStudResponse.errorEmptyId

            val current = StudentTable.select { StudentTable.id eq id.asInt() }
                .firstOrNull()
                ?.let { StudentTable.from(it) }

            when {
                current == null -> DbStudResponse.errorNotFound
                current.lock != lock -> DbStudResponse.errorConcurrent(lock, current)
                else -> block(current)
            }
        }

    override suspend fun updateStud(rq: DbStudRequest): DbStudResponse =
        update(rq.stud.id, rq.stud.lock) {
            StudentTable.update({
                (StudentTable.id eq rq.stud.id.asInt()) and (StudentTable.lock eq rq.stud.lock.asString())
            }) {
                to(it, rq.stud, ranfomInt, randomUuid)
            }
            read(rq.stud.id)
        }

    override suspend fun deleteStud(rq: DbStudIdRequest): DbStudResponse = update(rq.id, rq.lock) {
        StudentTable.deleteWhere {
            (StudentTable.id eq rq.id.asInt()) and (StudentTable.lock eq rq.lock.asString())
        }
        DbStudResponse.success(it)
    }

    override suspend fun searchStud(rq: DbStudFilterRequest): DbStudsResponse =
        transactionWrapper({
            val res = StudentTable.select {
                buildList {
                    add(Op.TRUE)
                    if (rq.sex != ContStudentSex.NONE) {
                        add(StudentTable.sex eq rq.sex)
                    }
                    if (rq.fioFilter.isNotBlank()) {
                        add(
                            (StudentTable.fio like "%${rq.fioFilter}%")
//                                or (StudentTable.description like "%${rq.titleFilter}%")
                        )
                    }
                }.reduce { a, b -> a and b }
            }
            DbStudsResponse(data = res.map { StudentTable.from(it) }, isSuccess = true)
        }, {
            DbStudsResponse.error(it.asContError())
        })
}
