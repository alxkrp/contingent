package ru.ak.contingent.app.rabbit

import ru.ak.contingent.app.rabbit.config.RabbitConfig
import ru.ak.contingent.app.rabbit.config.RabbitExchangeConfiguration
import ru.ak.contingent.app.rabbit.controller.RabbitController
import ru.ak.contingent.app.rabbit.processor.RabbitDirectProcessorV1
import ru.ak.contingent.biz.ContStudentProcessor

fun main() {
    val config = RabbitConfig()
    val adProcessor = ContStudentProcessor()

    val producerConfigV1 = RabbitExchangeConfiguration(
        keyIn = "in",
        keyOut = "out",
        exchange = "transport-exchange",
        queueIn = "queue",
        queueOut = "queue-out",
        consumerTag = "consumer",
        exchangeType = "direct"
    )

    val processor by lazy {
        RabbitDirectProcessorV1(
            config = config,
            processorConfig = producerConfigV1,
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
