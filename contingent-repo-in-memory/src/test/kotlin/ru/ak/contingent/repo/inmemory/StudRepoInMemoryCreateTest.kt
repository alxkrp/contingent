package ru.ak.contingent.repo.inmemory

import ru.ak.contingent.backend.repo.tests.RepoStudCreateTest

class StudRepoInMemoryCreateTest : RepoStudCreateTest() {
    override val repo = StudRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}