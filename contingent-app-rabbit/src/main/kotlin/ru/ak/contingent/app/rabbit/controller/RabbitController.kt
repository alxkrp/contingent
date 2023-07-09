package ru.ak.contingent.app.rabbit.controller

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.ak.contingent.app.rabbit.RabbitProcessorBase
import ru.ak.contingent.app.rabbit.config.rabbitLogger

class RabbitController(
    private val processors: Set<RabbitProcessorBase>
) {

    fun start() = runBlocking {
        rabbitLogger.info("start init processors")
        processors.forEach {
            try {
                launch { it.process() }
            } catch (e: RuntimeException) {
                e.printStackTrace()
            }
        }
    }
}
