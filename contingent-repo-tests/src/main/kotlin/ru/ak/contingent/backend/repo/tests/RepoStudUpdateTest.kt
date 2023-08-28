package ru.ak.contingent.backend.repo.tests

import ru.ak.contingent.common.models.ContStudent
import ru.ak.contingent.common.models.ContStudentId
import ru.ak.contingent.common.models.ContStudentLock
import ru.ak.contingent.common.models.ContStudentSex
import ru.ak.contingent.common.repo.DbStudRequest
import ru.ak.contingent.common.repo.IStudRepository
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class RepoStudUpdateTest {
    abstract val repo: IStudRepository
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    private val updateIdNotFound = ContStudentId(-1)
    private val lockBad = ContStudentLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = ContStudentLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        ContStudent(
            id = updateSucc.id,
            fio = "update student",
            sex = ContStudentSex.M,
            semester = 2,
            eduYear = 2023,
            specialityId = 3,
            facultyId = 2,
            groupNum = "102-л",
            lock = initObjects.first().lock,
        )
    }
    private val reqUpdateNotFound = ContStudent(
        id = updateIdNotFound,
        fio = "update student not found",
        sex = ContStudentSex.M,
        semester = 2,
        eduYear = 2023,
        specialityId = 3,
        facultyId = 2,
        groupNum = "102-л",
        lock = initObjects.first().lock,
    )
    private val reqUpdateConc by lazy {
        ContStudent(
            id = updateConc.id,
            fio = "update student not found",
            lock = lockBad,
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateStud(DbStudRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.fio, result.data?.fio)
        assertEquals(reqUpdateSucc.sex, result.data?.sex)
        assertEquals(reqUpdateSucc.semester, result.data?.semester)
        assertEquals(reqUpdateSucc.eduYear, result.data?.eduYear)
        assertEquals(reqUpdateSucc.specialityId, result.data?.specialityId)
        assertEquals(reqUpdateSucc.facultyId, result.data?.facultyId)
        assertEquals(reqUpdateSucc.groupNum, result.data?.groupNum)
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
