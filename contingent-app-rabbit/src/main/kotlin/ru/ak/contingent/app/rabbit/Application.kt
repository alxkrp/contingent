package ru.ak.contingent.app.rabbit

import ru.ak.contingent.app.rabbit.config.RabbitConfig
import ru.ak.contingent.app.rabbit.config.RabbitExchangeConfiguration
import ru.ak.contingent.app.rabbit.controller.RabbitController
import ru.ak.contingent.app.rabbit.processor.RabbitDirectProcessor
import ru.ak.contingent.biz.ContStudentProcessor

fun main() {
    val config = RabbitConfig()
    val adProcessor = ContStudentProcessor()

    val producerConfig = RabbitExchangeConfiguration(
        keyIn = "in",
        keyOut = "out",
        exchange = "transport-exchange",
        queueIn = "queue",
        queueOut = "queue-out",
        consumerTag = "consumer",
        exchangeType = "direct"
    )

    val processor by lazy {
        RabbitDirectProcessor(
            config = config,
            processorConfig = producerConfig,
            processor = adProcessor
        )
    }

    val controller by lazy {
        RabbitController(
            processors = setOf(processor)
        )
    }

    controller.start()
}
