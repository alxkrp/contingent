package ru.ak.contingent.biz

import kotlinx.coroutines.test.runTest
import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.*
import ru.ak.contingent.common.stubs.ContStubs
import ru.ak.contingent.stubs.ContStudentStub
import kotlin.test.Test
import kotlin.test.assertEquals

class StudentReadStubTest {

    private val processor = ContStudentProcessor()
    val id = ContStudentId(777)

    @Test
    fun read() = runTest {

        val ctx = ContContext(
            command = ContCommand.READ,
            state = ContState.NONE,
            workMode = ContWorkMode.STUB,
            stubCase = ContStubs.SUCCESS,
            studRequest = ContStudent(
                id = id,
            ),
        )

        processor.exec(ctx)

        with (ContStudentStub.get()) {
            assertEquals(id, ctx.studResponse.id)
            assertEquals(fio, ctx.studResponse.fio)
            assertEquals(sex, ctx.studResponse.sex)
            assertEquals(semester, ctx.studResponse.semester)
            assertEquals(eduYear, ctx.studResponse.eduYear)
            assertEquals(specialityId, ctx.studResponse.specialityId)
            assertEquals(facultyId, ctx.studResponse.facultyId)
            assertEquals(groupNum, ctx.studResponse.groupNum)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = ContContext(
            command = ContCommand.READ,
            state = ContState.NONE,
            workMode = ContWorkMode.STUB,
            stubCase = ContStubs.BAD_ID,
            studRequest = ContStudent(),
        )
        processor.exec(ctx)
        assertEquals(ContStudent(), ctx.studResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = ContContext(
            command = ContCommand.READ,
            state = ContState.NONE,
            workMode = ContWorkMode.STUB,
            stubCase = ContStubs.DB_ERROR,
            studRequest = ContStudent(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(ContStudent(), ctx.studResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = ContContext(
            command = ContCommand.READ,
            state = ContState.NONE,
            workMode = ContWorkMode.STUB,
            stubCase = ContStubs.BAD_FIO,
            studRequest = ContStudent(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(ContStudent(), ctx.studResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
