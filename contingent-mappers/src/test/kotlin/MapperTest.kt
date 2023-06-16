package ru.ak.contingent.mappers

import org.junit.Test
import ru.ak.contingent.api.models.*
import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.*
import ru.ak.contingent.common.stubs.ContStubs
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = StudentCreateRequest(
            requestId = "1234",
            debug = ContingentDebug(
                mode = ContingentRequestDebugMode.STUB,
                stub = ContingentRequestDebugStubs.SUCCESS,
            ),
            student = StudentCreateObject(
                fio = "Иванов",
            ),
        )

        val context = ContContext()
        context.fromTransport(req)

        assertEquals(ContStubs.SUCCESS, context.stubCase)
        assertEquals(ContWorkMode.STUB, context.workMode)
        assertEquals("Иванов", context.studRequest.fio)
    }

    @Test
    fun toTransport() {
        val context = ContContext(
            requestId = ContRequestId("1234"),
            command = ContCommand.CREATE,
            studResponse = ContStudent(
                fio = "Иванов",
            ),
            errors = mutableListOf(
                ContError(
                    code = "err",
                    group = "request",
                    field = "fio",
                    message = "wrong fio",
                )
            ),
            state = ContState.RUNNING,
        )

        val req = context.toTransportStudent() as StudentCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("Иванов", req.student?.fio)
    }
}
