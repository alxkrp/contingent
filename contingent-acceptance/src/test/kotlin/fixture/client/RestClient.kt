package ru.ak.contingent.fixture.client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.ktor.http.*
import ru.ak.contingent.blackbox.fixture.client.Client
import ru.ak.contingent.blackbox.fixture.docker.DockerCompose

/**
 * Отправка запросов по http/rest
 */
class RestClient(dockerCompose: DockerCompose) : Client {
    private val urlBuilder by lazy { dockerCompose.inputUrl }
    private val client = HttpClient(OkHttp)
    override suspend fun sendAndReceive(path: String, request: String): String {
        val url = urlBuilder.apply {
            path(path)
        }.build()

        val resp = client.post {
            url(url)
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
                append(HttpHeaders.Authorization,"Basic YWRtaW46cGFzc3dvcmQ=")
            }
            accept(ContentType.Application.Json)
            setBody(request)

        }.call

        return resp.body()
    }
}