package ru.ak.contingent.springapp.api.controller

import org.springframework.security.core.context.SecurityContextHolder
import ru.ak.contingent.api.models.IRequest
import ru.ak.contingent.api.models.IResponse
import ru.ak.contingent.logging.IContLogWrapper
import ru.ak.contingent.mappers.fromTransport
import ru.ak.contingent.mappers.toLog
import ru.ak.contingent.mappers.toTransportStudent
import ru.ak.contingent.springapp.base.toModel
import ru.ak.contingent.springapp.config.ContAppSettings
import ru.ak.contingent.springapp.process

suspend inline fun <reified Q : IRequest, reified R : IResponse> process(
    appSettings: ContAppSettings,
    request: Q,
    logger: IContLogWrapper,
    logId: String,
): R = appSettings.processor.process(logger, logId,
    fromTransport = {
        principal = SecurityContextHolder.getContext().authentication.toModel()
        fromTransport(request)
    },
    sendResponse = { toTransportStudent() as R },
    toLog = { toLog("spring") }
)
