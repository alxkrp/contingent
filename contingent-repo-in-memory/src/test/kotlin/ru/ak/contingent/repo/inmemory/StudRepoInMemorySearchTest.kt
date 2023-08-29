package ru.ak.contingent.repo.inmemory

import ru.ak.contingent.backend.repo.tests.RepoStudSearchTest
import ru.ak.contingent.common.repo.IStudRepository

class StudRepoInMemorySearchTest : RepoStudSearchTest() {
    override val repo: IStudRepository = StudRepoInMemory(
        initObjects = initObjects
    )
}
