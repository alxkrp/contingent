package ru.ak.contingent.blackbox.test

import io.kotest.core.annotation.Ignored
import ru.ak.contingent.blackbox.docker.SpringDockerCompose
import ru.ak.contingent.blackbox.fixture.BaseFunSpec
import ru.ak.contingent.blackbox.fixture.db.PostgresClearer
import ru.ak.contingent.fixture.client.RestClient

open class AccRestSpringTest : BaseFunSpec(SpringDockerCompose, PostgresClearer(), {
    val restClient = RestClient(SpringDockerCompose)
    testStubApi(restClient, "rest ")
    testApi(restClient, "rest ")
})
