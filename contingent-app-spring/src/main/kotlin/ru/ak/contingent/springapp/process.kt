package ru.ak.contingent.springapp

import kotlinx.datetime.Clock
import ru.ak.contingent.biz.ContStudentProcessor
import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.helpers.asContError
import ru.ak.contingent.common.helpers.fail
import ru.ak.contingent.common.models.ContCommand
import ru.ak.contingent.logging.IContLogWrapper

suspend fun <T> ContStudentProcessor.process(
    logger: IContLogWrapper,
    logId: String,
    fromTransport: suspend ContContext.() -> Unit,
    sendResponse: suspend ContContext.() -> T,
    toLog: ContContext.(logId: String) -> Any): T {
    var command = ContCommand.NONE
    return try {
        val ctx = ContContext(
            timeStart = Clock.System.now(),
        )

        logger.doWithLogging(id = logId) {
            ctx.fromTransport()
            command = ctx.command

            logger.info(
                msg = "$command request is got",
                data = ctx.toLog("${logId}-got")
            )
            exec(ctx)
            logger.info(
                msg = "$command request is handled",
                data = ctx.toLog("${logId}-handled")
            )
            ctx.sendResponse()
        }
    } catch (e: Throwable) {
        logger.doWithLogging(id = "${logId}-failure") {
            logger.error(msg = "$command handling failed")

            val ctx = ContContext(
                timeStart = Clock.System.now(),
                command = command
            )

            ctx.fail(e.asContError())
            exec(ctx)
            sendResponse(ctx)
        }
    }
}
