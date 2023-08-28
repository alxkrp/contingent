package ru.ak.contingent.backend.repo.tests

import ru.ak.contingent.common.models.ContStudent
import ru.ak.contingent.common.models.ContStudentId
import ru.ak.contingent.common.models.ContStudentLock
import ru.ak.contingent.common.models.ContStudentSex
import ru.ak.contingent.common.repo.DbStudRequest
import ru.ak.contingent.common.repo.IStudRepository
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class RepoStudCreateTest {
    abstract val repo: IStudRepository

    protected open val lockNew: ContStudentLock = ContStudentLock("20000000-0000-0000-0000-000000000002")

    private val createObj = ContStudent(
        fio = "Иванов Иван Иванович",
        sex = ContStudentSex.M,
        semester = 2,
        eduYear = 2023,
        specialityId = 3,
        facultyId = 2,
        groupNum = "102-л",
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createStud(DbStudRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: ContStudentId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.fio, result.data?.fio)
        assertEquals(expected.sex, result.data?.sex)
        assertEquals(expected.semester, result.data?.semester)
        assertEquals(expected.eduYear, result.data?.eduYear)
        assertEquals(expected.specialityId, result.data?.specialityId)
        assertEquals(expected.facultyId, result.data?.facultyId)
        assertEquals(expected.groupNum, result.data?.groupNum)
    }

    companion object : BaseInitStuds("create") {
        override val initObjects: List<ContStudent> = emptyList()
    }
}
