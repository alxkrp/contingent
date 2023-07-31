package ru.ak.contingent.logging

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

/**
 * Generate internal MpLogContext logger
 *
 * @param logger Logback instance from [LoggerFactory.getLogger()]
 */
fun contLoggerLogback(logger: Logger): IContLogWrapper = ContLogWrapperLogback(
    logger = logger,
    loggerId = logger.name,
)

fun contLoggerLogback(clazz: KClass<*>): IContLogWrapper = contLoggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)
@Suppress("unused")
fun contLoggerLogback(loggerId: String): IContLogWrapper = contLoggerLogback(LoggerFactory.getLogger(loggerId) as Logger)
