package ru.ak.contingent.repo.inmemory

import ru.ak.contingent.backend.repo.tests.RepoStudReadTest
import ru.ak.contingent.common.repo.IStudRepository

class StudRepoInMemoryReadTest: RepoStudReadTest() {
    override val repo: IStudRepository = StudRepoInMemory(
        initObjects = initObjects
    )
}
