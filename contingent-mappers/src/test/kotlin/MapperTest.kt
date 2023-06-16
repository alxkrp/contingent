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
                sex = Sex.M,
                semester = 2,
                eduYear = 2023,
                specialityId = 5,
                facultyId = 10,
                groupNum = "123-л",
            ),
        )

        val context = ContContext()
        context.fromTransport(req)

        assertEquals(ContStubs.SUCCESS, context.stubCase)
        assertEquals(ContWorkMode.STUB, context.workMode)
        assertEquals("Иванов", context.studRequest.fio)
        assertEquals(ContStudentSex.M, context.studRequest.sex)
        assertEquals(2, context.studRequest.semester)
        assertEquals(2023, context.studRequest.eduYear)
        assertEquals(5, context.studRequest.specialityId)
        assertEquals(10, context.studRequest.facultyId)
        assertEquals("123-л", context.studRequest.groupNum)
    }

    @Test
    fun toTransport() {
        val context = ContContext(
            requestId = ContRequestId("1234"),
            command = ContCommand.CREATE,
            studResponse = ContStudent(
                fio = "Иванов",
                sex = ContStudentSex.M,
                semester = 2,
                eduYear = 2023,
                specialityId = 5,
                facultyId = 10,
                groupNum = "123-л",
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
        assertEquals(Sex.M, req.student?.sex)
        assertEquals(2, req.student?.semester)
        assertEquals(2023, req.student?.eduYear)
        assertEquals(5, req.student?.specialityId)
        assertEquals(10, req.student?.facultyId)
        assertEquals("123-л", req.student?.groupNum)
    }
}
