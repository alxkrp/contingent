package ru.ak.contingent.app.rabbit

import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.testcontainers.containers.RabbitMQContainer
import ru.ak.contingent.api.apiMapper
import ru.ak.contingent.api.models.*
import ru.ak.contingent.app.rabbit.config.RabbitConfig
import ru.ak.contingent.app.rabbit.config.RabbitConfig.Companion.RABBIT_PASSWORD
import ru.ak.contingent.app.rabbit.config.RabbitConfig.Companion.RABBIT_USER
import ru.ak.contingent.app.rabbit.config.RabbitExchangeConfiguration
import ru.ak.contingent.app.rabbit.controller.RabbitController
import ru.ak.contingent.app.rabbit.processor.RabbitDirectProcessor
import ru.ak.contingent.stubs.ContStudentStub
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RabbitContingentTest {

    companion object {
        const val EXCHANGE_TYPE = "direct"
        const val TRANSPORT_EXCHANGE = "transport-exchange"
    }

    private val container by lazy {
        RabbitMQContainer("rabbitmq:latest").apply {
            withExposedPorts(5672, 15672)
            withUser(RABBIT_USER, RABBIT_PASSWORD)
            start()
        }
    }

    private val config by lazy {
        RabbitConfig(
            port = container.getMappedPort(5672),
            host = container.host
        )
    }

    private val processorConfig = RabbitExchangeConfiguration(
        keyIn = "in-",
        keyOut = "out-",
        exchange = TRANSPORT_EXCHANGE,
        queueIn = "queue",
        queueOut = "queue-out",
        consumerTag = "consumer",
        exchangeType = EXCHANGE_TYPE
    )

    private val processor by lazy {
        RabbitDirectProcessor(
            config = config,
            processorConfig = processorConfig
        )
    }

    private val controller by lazy {
        RabbitController(
            processors = setOf(processor)
        )
    }

    @BeforeTest
    fun tearUp() {
        println("init controller")
        GlobalScope.launch {
            controller.start()
        }
        Thread.sleep(6000)
        // await when controller starts producers
        println("controller initiated")
    }

    @Test
    fun studentCreateTest() {
        println("start test")
        val processorConfig = processor.processorConfig
        val keyIn = processorConfig.keyIn

        val connection1 = ConnectionFactory().apply {
            host = config.host
            port = config.port
            username = config.user
            password = config.password
        }.newConnection()

        connection1.createChannel().use { channel ->
            var responseJson = ""
            channel.exchangeDeclare(processorConfig.exchange, EXCHANGE_TYPE)
            val queueOut = channel.queueDeclare().queue
            channel.queueBind(queueOut, processorConfig.exchange, processorConfig.keyOut)
            val deliverCallback = DeliverCallback { consumerTag, delivery ->
                responseJson = String(delivery.body, Charsets.UTF_8)
                println(" [x] Received by $consumerTag: '$responseJson'")
            }
            channel.basicConsume(queueOut, true, deliverCallback, CancelCallback { })

            channel.basicPublish(processorConfig.exchange, keyIn, null, apiMapper.writeValueAsBytes(studentCreate))

            Thread.sleep(3000)
            // waiting for message processing
            println("RESPONSE: $responseJson")
            val response = apiMapper.readValue(responseJson, StudentCreateResponse::class.java)
            val expected = ContStudentStub.get()

            assertEquals(expected.fio, response.student?.fio)
            assertEquals(expected.sex.toString(), response.student?.sex?.value)
            assertEquals(expected.semester, response.student?.semester)
            assertEquals(expected.eduYear, response.student?.eduYear)
            assertEquals(expected.specialityId, response.student?.specialityId)
            assertEquals(expected.facultyId, response.student?.facultyId)
            assertEquals(expected.groupNum, response.student?.groupNum)
        }
    }

    private val studentCreate = with(ContStudentStub.get()) {
        StudentCreateRequest(
            student = StudentCreateObject(
                fio = fio,
                sex = Sex.M,
                semester = semester,
                eduYear = eduYear,
                specialityId = specialityId,
                facultyId = facultyId,
                groupNum = groupNum
            ),
            requestType = "create",
            debug = ContingentDebug(
                mode = ContingentRequestDebugMode.STUB,
                stub = ContingentRequestDebugStubs.SUCCESS
            )
        )
    }
}
