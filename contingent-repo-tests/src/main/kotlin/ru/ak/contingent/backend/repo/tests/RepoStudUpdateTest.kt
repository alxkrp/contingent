package ru.ak.contingent.backend.repo.tests

import ru.ak.contingent.common.models.ContStudent
import ru.ak.contingent.common.models.ContStudentId
import ru.ak.contingent.common.models.ContStudentLock
import ru.ak.contingent.common.repo.DbStudRequest
import ru.ak.contingent.common.repo.IStudRepository
import kotlin.test.Test
import kotlin.test.assertEquals


abstract class RepoStudUpdateTest {
    abstract val repo: IStudRepository
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = ContStudentId(-1)
    protected val lockBad = ContStudentLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = ContStudentLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        ContStudent(
            id = updateSucc.id,
            fio = "update object",
//            description = "update object description",
//            ownerId = MkplUserId("owner-123"),
//            visibility = MkplVisibility.VISIBLE_TO_GROUP,
//            adType = MkplDealSide.SUPPLY,
            lock = initObjects.first().lock,
        )
    }
    private val reqUpdateNotFound = ContStudent(
        id = updateIdNotFound,
        fio = "update object not found",
//        description = "update object not found description",
//        ownerId = MkplUserId("owner-123"),
//        visibility = MkplVisibility.VISIBLE_TO_GROUP,
//        adType = MkplDealSide.SUPPLY,
        lock = initObjects.first().lock,
    )
    private val reqUpdateConc by lazy {
        ContStudent(
            id = updateConc.id,
            fio = "update object not found",
//            description = "update object not found description",
//            ownerId = MkplUserId("owner-123"),
//            visibility = MkplVisibility.VISIBLE_TO_GROUP,
//            adType = MkplDealSide.SUPPLY,
            lock = lockBad,
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateStud(DbStudRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.fio, result.data?.fio)
//        assertEquals(reqUpdateSucc.description, result.data?.description)
//        assertEquals(reqUpdateSucc.adType, result.data?.adType)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateStud(DbStudRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateStud(DbStudRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitStuds("update") {
        override val initObjects: List<ContStudent> = listOf(
            createInitTestModel(111, "update"),
            createInitTestModel(222, "updateConc"),
        )
    }
}
