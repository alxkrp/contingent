package ru.ak.contingent.backend.repo.tests

import ru.ak.contingent.common.models.ContStudent
import ru.ak.contingent.common.models.ContStudentId
import ru.ak.contingent.common.repo.DbStudIdRequest
import ru.ak.contingent.common.repo.IStudRepository
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class RepoStudReadTest {
    abstract val repo: IStudRepository
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readStud(DbStudIdRequest(readSucc.id))

        assertEquals(true, result.isSuccess)
        assertEquals(readSucc, result.data)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readStud(DbStudIdRequest(notFoundId))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitStuds("delete") {
        override val initObjects: List<ContStudent> = listOf(
            createInitTestModel(111, "read")
        )

        val notFoundId = ContStudentId(-1)

    }
}
