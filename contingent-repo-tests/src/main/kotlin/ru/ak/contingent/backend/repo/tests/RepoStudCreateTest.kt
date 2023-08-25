package ru.ak.contingent.backend.repo.tests

import ru.ak.contingent.common.models.ContStudent
import ru.ak.contingent.common.models.ContStudentId
import ru.ak.contingent.common.models.ContStudentLock
import ru.ak.contingent.common.repo.DbStudRequest
import ru.ak.contingent.common.repo.IStudRepository
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class RepoStudCreateTest {
    abstract val repo: IStudRepository

    protected open val lockNew: ContStudentLock = ContStudentLock("20000000-0000-0000-0000-000000000002")

    private val createObj = ContStudent(
        fio = "Иванов Иван Иванович",
//        description = "create object description",
//        ownerId = MkplUserId("owner-123"),
//        visibility = MkplVisibility.VISIBLE_TO_GROUP,
//        adType = MkplDealSide.SUPPLY,
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createStud(DbStudRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: ContStudentId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.fio, result.data?.fio)
//        assertEquals(expected.description, result.data?.description)
//        assertEquals(expected.adType, result.data?.adType)
//        assertNotEquals(MkplAdId.NONE, result.data?.id)
//        assertEquals(emptyList(), result.errors)
//        assertEquals(lockNew, result.data?.lock)
    }

    companion object : BaseInitStuds("create") {
        override val initObjects: List<ContStudent> = emptyList()
    }
}
