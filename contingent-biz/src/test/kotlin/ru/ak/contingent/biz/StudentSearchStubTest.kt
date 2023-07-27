package ru.ak.contingent.biz

import kotlinx.coroutines.test.runTest
import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.*
import ru.ak.contingent.common.stubs.ContStubs
import ru.ak.contingent.stubs.ContStudentStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class StudentSearchStubTest {

    private val processor = ContStudentProcessor()
    val filter = ContStudentFilter(searchString = "Иванов")

    @Test
    fun search() = runTest {

        val ctx = ContContext(
            command = ContCommand.SEARCH,
            state = ContState.NONE,
            workMode = ContWorkMode.STUB,
            stubCase = ContStubs.SUCCESS,
            studFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.studsResponse.size > 1)
        val first = ctx.studsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.fio.contains(filter.searchString))
        with (ContStudentStub.get()) {
            assertEquals(sex, first.sex)
            assertEquals(semester, first.semester)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = ContContext(
            command = ContCommand.SEARCH,
            state = ContState.NONE,
            workMode = ContWorkMode.STUB,
            stubCase = ContStubs.BAD_ID,
            studFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(ContStudent(), ctx.studResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = ContContext(
            command = ContCommand.SEARCH,
            state = ContState.NONE,
            workMode = ContWorkMode.STUB,
            stubCase = ContStubs.DB_ERROR,
            studFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(ContStudent(), ctx.studResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = ContContext(
            command = ContCommand.SEARCH,
            state = ContState.NONE,
            workMode = ContWorkMode.STUB,
            stubCase = ContStubs.BAD_FIO,
            studFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(ContStudent(), ctx.studResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
