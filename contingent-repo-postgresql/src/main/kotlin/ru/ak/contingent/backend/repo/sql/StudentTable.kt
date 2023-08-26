package ru.ak.contingent.backend.repo.sql

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.ak.contingent.common.models.ContStudent
import ru.ak.contingent.common.models.ContStudentId
import ru.ak.contingent.common.models.ContStudentLock
import ru.ak.contingent.common.models.ContStudentSex

object StudentTable : Table("student") {
    val id = integer("id")
    val fio = varchar("fio", 100)
    val sex = enumeration("visibility", ContStudentSex::class)
    val lock = varchar("lock", 50)

    override val primaryKey = PrimaryKey(id)

    fun from(res: InsertStatement<Number>) = ContStudent(
        id = ContStudentId(res[id].toInt()),
        fio = res[fio],
        sex = res[sex],
        lock = ContStudentLock(res[lock])
    )

    fun from(res: ResultRow) = ContStudent(
        id = ContStudentId(res[id].toInt()),
        fio = res[fio],
        sex = res[sex],
        lock = ContStudentLock(res[lock])
    )

    fun to(it: UpdateBuilder<*>, student: ContStudent, randomId: () -> Int, randomUuid: () -> String) {
        it[id] = student.id.takeIf { it != ContStudentId.NONE }?.asInt() ?: randomId()
        it[fio] = student.fio
        it[sex] = student.sex
        it[lock] = student.lock.takeIf { it != ContStudentLock.NONE }?.asString() ?: randomUuid()
    }

}
