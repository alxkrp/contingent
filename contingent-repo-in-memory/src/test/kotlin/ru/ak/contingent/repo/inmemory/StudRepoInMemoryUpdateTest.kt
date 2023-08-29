package ru.ak.contingent.repo.inmemory

import ru.ak.contingent.backend.repo.tests.RepoStudUpdateTest
import ru.ak.contingent.common.repo.IStudRepository

class StudRepoInMemoryUpdateTest : RepoStudUpdateTest() {
    override val repo: IStudRepository = StudRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}
