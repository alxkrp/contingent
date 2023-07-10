package ru.ak.contingent.app.rabbit

import com.rabbitmq.client.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import mu.KotlinLogging
import ru.ak.contingent.app.rabbit.config.RabbitConfig
import ru.ak.contingent.app.rabbit.config.RabbitExchangeConfiguration
import ru.ak.contingent.app.rabbit.config.rabbitLogger
import ru.ak.contingent.common.ContContext
import kotlin.coroutines.CoroutineContext

private val log = KotlinLogging.logger {}

/**
 * Абстрактный класс для процессоров-консьюмеров RabbitMQ
 * @property config - настройки подключения
 * @property processorConfig - настройки Rabbit exchange
 */
abstract class RabbitProcessorBase(
    private val config: RabbitConfig,
    val processorConfig: RabbitExchangeConfiguration
) {
    suspend fun process(dispatcher: CoroutineContext = Dispatchers.IO) {
        rabbitLogger.info("create connection")
        withContext(dispatcher) {
            ConnectionFactory().apply {
                host = config.host
                port = config.port
                username = config.user
                password = config.password
            }.newConnection().use { connection ->
                connection.createChannel().use { channel ->
                    val deliveryCallback = channel.getDeliveryCallback()
                    val cancelCallback = getCancelCallback()
                    runBlocking {
                        channel.describeAndListen(deliveryCallback, cancelCallback)
                    }
                }
            }
        }
    }

    /**
     * Обработка поступившего сообщения в deliverCallback
     */
    protected abstract suspend fun Channel.processMessage(message: Delivery, context: ContContext)

    /**
     * Обработка ошибок
     */
    protected abstract fun Channel.onError(e: Throwable, context: ContContext)

    /**
     * Callback, который вызывается при доставке сообщения консьюмеру
     */
    private fun Channel.getDeliveryCallback(): DeliverCallback = DeliverCallback { _, message ->
        runBlocking {
            val context = ContContext().apply {
                timeStart = Clock.System.now()
            }
            kotlin.runCatching {
                processMessage(message, context)
            }.onFailure {
                onError(it, context)
            }
        }
    }

    /**
     * Callback, вызываемый при отмене консьюмера
     */
    private fun getCancelCallback() = CancelCallback {
        log.info("[$it] was cancelled")
    }

    private suspend fun Channel.describeAndListen(
        deliverCallback: DeliverCallback,
        cancelCallback: CancelCallback
    ) {
        withContext(Dispatchers.IO) {
            log.info("start describing")
            exchangeDeclare(processorConfig.exchange, processorConfig.exchangeType)
            // Объявляем очередь (не сохраняется при перезагрузке сервера; неэксклюзивна - доступна другим соединениям;
            // не удаляется, если не используется)
            queueDeclare(processorConfig.queueIn, false, false, false, null)
            queueDeclare(processorConfig.queueOut, false, false, false, null)
            // связываем обменник с очередью по ключу (сообщения будут поступать в данную очередь с данного обменника при совпадении ключа)
            queueBind(processorConfig.queueIn, processorConfig.exchange, processorConfig.keyIn)
            queueBind(processorConfig.queueOut, processorConfig.exchange, processorConfig.keyOut)
            // запуск консьюмера с автоотправкой подтверждение при получении сообщения
            basicConsume(processorConfig.queueIn, true, processorConfig.consumerTag, deliverCallback, cancelCallback)
            log.info("finish describing")
            while (isOpen) {
                kotlin.runCatching {
                    delay(100)
                }.onFailure { e ->
                    e.printStackTrace()
                }
            }

            log.info("Channel for [${processorConfig.consumerTag}] was closed.")
        }
    }
}