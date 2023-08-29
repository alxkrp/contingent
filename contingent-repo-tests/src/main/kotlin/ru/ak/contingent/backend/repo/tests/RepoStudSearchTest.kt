package ru.ak.contingent.backend.repo.tests

import ru.ak.contingent.common.models.ContStudent
import ru.ak.contingent.common.models.ContStudentSex
import ru.ak.contingent.common.repo.DbStudFilterRequest
import ru.ak.contingent.common.repo.IStudRepository
import kotlin.test.Test
import kotlin.test.assertEquals


abstract class RepoStudSearchTest {
    abstract val repo: IStudRepository

    protected open val initializedObjects: List<ContStudent> = initObjects

    @Test
    fun searchByFio() = runRepoTest {
        val result = repo.searchStud(DbStudFilterRequest(fioFilter = "Иванов", sex = ContStudentSex.NONE))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[1], initializedObjects[3]).sortedBy { it.id.asInt() }
        assertEquals(expected, result.data?.sortedBy { it.id.asInt() })
        assertEquals(emptyList(), result.errors)
    }

    companion object: BaseInitStuds("search") {

        override val initObjects: List<ContStudent> = listOf(
            createInitTestModel(1, "Пупкин Сидор"),
            createInitTestModel(2, "Иванов Иван"),
            createInitTestModel(3, "Петров Роман"),
            createInitTestModel(4, "Иванов Василий"),
        )
    }
}
