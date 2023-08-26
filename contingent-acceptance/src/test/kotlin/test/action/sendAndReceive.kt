package ru.ak.contingent.blackbox.test.action

import mu.KotlinLogging
import ru.ak.contingent.api.apiRequestSerialize
import ru.ak.contingent.api.apiResponseDeserialize
import ru.ak.contingent.api.models.IRequest
import ru.ak.contingent.api.models.IResponse
import ru.ak.contingent.blackbox.fixture.client.Client

private val log = KotlinLogging.logger {}

suspend fun Client.sendAndReceive(path: String, request: IRequest): IResponse {
    val requestBody = apiRequestSerialize(request)
    log.info { "Send to $path\n$requestBody" }

    val responseBody = sendAndReceive(path, requestBody)
    log.info { "Received\n$responseBody" }

    return apiResponseDeserialize(responseBody)
}