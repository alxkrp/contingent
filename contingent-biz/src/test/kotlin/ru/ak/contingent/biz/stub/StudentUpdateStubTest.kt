package ru.ak.contingent.biz.stub

import kotlinx.coroutines.test.runTest
import ru.ak.contingent.biz.ContStudentProcessor
import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.*
import ru.ak.contingent.common.stubs.ContStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class StudentUpdateStubTest {

    private val processor = ContStudentProcessor()
    val id = ContStudentId(777)
    val fio = "Иванов Иван Иванович"
    val sex = ContStudentSex.M
    val semester = 2
    val eduYear = 2023
    val specialityId = 3
    val facultyId = 2
    val groupNum = "102-л"

    @Test
    fun create() = runTest {

        val ctx = ContContext(
            command = ContCommand.UPDATE,
            state = ContState.NONE,
            workMode = ContWorkMode.STUB,
            stubCase = ContStubs.SUCCESS,
            studRequest = ContStudent(
                id = id,
                fio = fio,
                sex = sex,
                semester = semester,
                eduYear = eduYear,
                specialityId = specialityId,
                facultyId = facultyId,
                groupNum = groupNum,
            )
        )

        processor.exec(ctx)

        assertEquals(id, ctx.studResponse.id)
        assertEquals(fio, ctx.studResponse.fio)
        assertEquals(sex, ctx.studResponse.sex)
        assertEquals(semester, ctx.studResponse.semester)
        assertEquals(eduYear, ctx.studResponse.eduYear)
        assertEquals(specialityId, ctx.studResponse.specialityId)
        assertEquals(facultyId, ctx.studResponse.facultyId)
        assertEquals(groupNum, ctx.studResponse.groupNum)
    }

    @Test
    fun badId() = runTest {
        val ctx = ContContext(
            command = ContCommand.UPDATE,
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
    fun badFio() = runTest {
        val ctx = ContContext(
            command = ContCommand.UPDATE,
            state = ContState.NONE,
            workMode = ContWorkMode.STUB,
            stubCase = ContStubs.BAD_FIO,
            studRequest = ContStudent(
                id = id,
                fio = "",
                sex = sex,
                semester = semester,
                eduYear = eduYear,
                specialityId = specialityId,
                facultyId = facultyId,
                groupNum = groupNum,
            )
        )

        processor.exec(ctx)

        assertEquals(ContStudent(), ctx.studResponse)
        assertEquals("fio", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
    @Test
    fun badSex() = runTest {
        val ctx = ContContext(
            command = ContCommand.UPDATE,
            state = ContState.NONE,
            workMode = ContWorkMode.STUB,
            stubCase = ContStubs.BAD_SEX,
            studRequest = ContStudent(
                id = id,
                fio = fio,
                sex = ContStudentSex.NONE,
                semester = semester,
                eduYear = eduYear,
                specialityId = specialityId,
                facultyId = facultyId,
                groupNum = groupNum,
            )
        )

        processor.exec(ctx)

        assertEquals(ContStudent(), ctx.studResponse)
        assertEquals("sex", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = ContContext(
            command = ContCommand.UPDATE,
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
            command = ContCommand.UPDATE,
            state = ContState.NONE,
            workMode = ContWorkMode.STUB,
            stubCase = ContStubs.BAD_SEARCH_STRING,
            studRequest = ContStudent(
                id = id,
                fio = "",
                sex = ContStudentSex.NONE,
                semester = semester,
                eduYear = eduYear,
                specialityId = specialityId,
                facultyId = facultyId,
                groupNum = groupNum,
            ),
        )

        processor.exec(ctx)

        assertEquals(ContStudent(), ctx.studResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
