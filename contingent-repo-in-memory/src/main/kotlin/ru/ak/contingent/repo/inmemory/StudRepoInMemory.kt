package ru.ak.contingent.repo.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.ak.contingent.backend.repo.inmemory.model.StudentEntity
import ru.ak.contingent.common.helpers.errorRepoConcurrency
import ru.ak.contingent.common.models.ContError
import ru.ak.contingent.common.models.ContStudent
import ru.ak.contingent.common.models.ContStudentId
import ru.ak.contingent.common.models.ContStudentLock
import ru.ak.contingent.common.repo.*
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class StudRepoInMemory(
    initObjects: List<ContStudent> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
    val ranfomInt: () ->Int = { Random.nextInt(1, 1000000)},
) : IStudRepository {

    private val cache = Cache.Builder<String, StudentEntity>()
        .expireAfterWrite(ttl)
        .build()
    private val mutex: Mutex = Mutex()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(ad: ContStudent) {
        val entity = StudentEntity(ad)
        if (entity.id == null) {
            return
        }
        cache.put(entity.id.toString(), entity)
    }

    override suspend fun createStud(rq: DbStudRequest): DbStudResponse {
        val key = ranfomInt()
        val student = rq.stud.copy(id = ContStudentId(key), lock = ContStudentLock(randomUuid()))
        val entity = StudentEntity(student)
        cache.put(key.toString(), entity)
        return DbStudResponse(
            data = student,
            isSuccess = true,
        )
    }

    override suspend fun readStud(rq: DbStudIdRequest): DbStudResponse {
        val key = rq.id.takeIf { it != ContStudentId.NONE }?.asInt().toString() ?: return resultErrorEmptyId
        return cache.get(key)
            ?.let {
                DbStudResponse(
                    data = it.toInternal(),
                    isSuccess = true,
                )
            } ?: resultErrorNotFound
    }

    override suspend fun updateStud(rq: DbStudRequest): DbStudResponse {
        val key = rq.stud.id.takeIf { it != ContStudentId.NONE }?.asInt().toString() ?: return resultErrorEmptyId
        val oldLock = rq.stud.lock.takeIf { it != ContStudentLock.NONE }?.asString() ?: return resultErrorEmptyLock
        val newAd = rq.stud.copy(lock = ContStudentLock(randomUuid()))
        val entity = StudentEntity(newAd)
        return mutex.withLock {
            val oldAd = cache.get(key)
            when {
                oldAd == null -> resultErrorNotFound
                oldAd.lock != oldLock -> DbStudResponse(
                    data = oldAd.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(ContStudentLock(oldLock), oldAd.lock?.let { ContStudentLock(it) }))
                )

                else -> {
                    cache.put(key, entity)
                    DbStudResponse(
                        data = newAd,
                        isSuccess = true,
                    )
                }
            }
        }
    }

    override suspend fun deleteStud(rq: DbStudIdRequest): DbStudResponse {
        val key = rq.id.takeIf { it != ContStudentId.NONE }?.asInt().toString() ?: return resultErrorEmptyId
        val oldLock = rq.lock.takeIf { it != ContStudentLock.NONE }?.asString() ?: return resultErrorEmptyLock
        return mutex.withLock {
            val oldAd = cache.get(key)
            when {
                oldAd == null -> resultErrorNotFound
                oldAd.lock != oldLock -> DbStudResponse(
                    data = oldAd.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(ContStudentLock(oldLock), oldAd.lock?.let { ContStudentLock(it) }))
                )

                else -> {
                    cache.invalidate(key)
                    DbStudResponse(
                        data = oldAd.toInternal(),
                        isSuccess = true,
                    )
                }
            }
        }
    }

    /**
     * Поиск объявлений по фильтру
     * Если в фильтре не установлен какой-либо из параметров - по нему фильтрация не идет
     */
    override suspend fun searchStud(rq: DbStudFilterRequest): DbStudsResponse {
        val result = cache.asMap().asSequence()
//            .filter { entry ->
//                rq.ownerId.takeIf { it != MkplUserId.NONE }?.let {
//                    it.asString() == entry.value.ownerId
//                } ?: true
//            }
//            .filter { entry ->
//                rq.dealSide.takeIf { it != MkplDealSide.NONE }?.let {
//                    it.name == entry.value.adType
//                } ?: true
//            }
            .filter { entry ->
                rq.fioFilter.takeIf { it.isNotBlank() }?.let {
                    entry.value.fio?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        return DbStudsResponse(
            data = result,
            isSuccess = true
        )
    }

    companion object {
        val resultErrorEmptyId = DbStudResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                ContError(
                    code = "id-empty",
                    group = "validation",
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )
        val resultErrorEmptyLock = DbStudResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                ContError(
                    code = "lock-empty",
                    group = "validation",
                    field = "lock",
                    message = "Lock must not be null or blank"
                )
            )
        )
        val resultErrorNotFound = DbStudResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                ContError(
                    code = "not-found",
                    field = "id",
                    message = "Not Found"
                )
            )
        )
    }
}
