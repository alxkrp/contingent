package ru.ak.contingent.repo.inmemory

import ru.ak.contingent.backend.repo.tests.RepoStudDeleteTest
import ru.ak.contingent.common.repo.IStudRepository

class StudRepoInMemoryDeleteTest : RepoStudDeleteTest() {
    override val repo: IStudRepository = StudRepoInMemory(
        initObjects = initObjects
    )
}
