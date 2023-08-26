package ru.ak.contingent.blackbox.test

import io.kotest.core.annotation.Ignored
import ru.ak.contingent.blackbox.docker.SpringDockerCompose
import ru.ak.contingent.blackbox.fixture.BaseFunSpec
import ru.ak.contingent.blackbox.fixture.docker.DockerCompose
import ru.ak.contingent.fixture.client.RestClient

@Ignored
open class AccRestTestBase(dockerCompose: DockerCompose) : BaseFunSpec(dockerCompose, {
    val restClient = RestClient(dockerCompose)
    testApi(restClient, "rest ")
})

class AccRestSpringTest : AccRestTestBase(SpringDockerCompose)
