package ru.ak.contingent.backend.repo.sql

import ru.ak.contingent.backend.repo.tests.*
import ru.ak.contingent.common.repo.IStudRepository

class RepoStudSQLCreateTest : RepoStudCreateTest() {
    override val repo: IStudRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}

class RepoStudSQLDeleteTest : RepoStudDeleteTest() {
    override val repo: IStudRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoStudSQLReStudTest : RepoStudReadTest() {
    override val repo: IStudRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoStudSQLSearchTest : RepoStudSearchTest() {
    override val repo: IStudRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoStudSQLUpdateTest : RepoStudUpdateTest() {
    override val repo: IStudRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}
