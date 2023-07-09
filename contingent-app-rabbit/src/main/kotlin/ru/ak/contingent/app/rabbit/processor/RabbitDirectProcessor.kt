package ru.ak.contingent.app.rabbit.processor

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Delivery
import ru.ak.contingent.api.apiMapper
import ru.ak.contingent.api.models.IRequest
import ru.ak.contingent.app.rabbit.RabbitProcessorBase
import ru.ak.contingent.app.rabbit.config.RabbitConfig
import ru.ak.contingent.app.rabbit.config.RabbitExchangeConfiguration
import ru.ak.contingent.biz.ContStudentProcessor
import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.helpers.addError
import ru.ak.contingent.common.helpers.asContError
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.mappers.fromTransport
import ru.ak.contingent.mappers.toTransportStudent

class RabbitDirectProcessorV1(
    config: RabbitConfig,
    processorConfig: RabbitExchangeConfiguration,
    private val processor: ContStudentProcessor = ContStudentProcessor(),
) : RabbitProcessorBase(config, processorConfig) {
    override suspend fun Channel.processMessage(message: Delivery, context: ContContext) {
        apiMapper.readValue(message.body, IRequest::class.java).run {
            context.fromTransport(this).also {
                println("TYPE: ${this::class.simpleName}")
            }
        }
        val response = processor.exec(context).run { context.toTransportStudent() }
        apiMapper.writeValueAsBytes(response).also {
            println("Publishing $response to ${processorConfig.exchange} exchange for keyOut ${processorConfig.keyOut}")
            basicPublish(processorConfig.exchange, processorConfig.keyOut, null, it)
        }.also {
            println("published")
        }
    }

    override fun Channel.onError(e: Throwable, context: ContContext) {
        e.printStackTrace()
        context.state = ContState.FAILING
        context.addError(error = arrayOf(e.asContError()))
        val response = context.toTransportStudent()
        apiMapper.writeValueAsBytes(response).also {
            basicPublish(processorConfig.exchange, processorConfig.keyOut, null, it)
        }
    }
}
