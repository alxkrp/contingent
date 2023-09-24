package ru.ak.contingent.biz.validation

import kotlinx.coroutines.test.runTest
import ru.ak.contingent.biz.ContStudentProcessor
import ru.ak.contingent.biz.addTestPrincipal
import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.*
import ru.ak.contingent.stubs.ContStudentStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = ContStudentStub.get()

fun validationFioCorrect(command: ContCommand, processor: ContStudentProcessor) = runTest {
    val ctx = ContContext(
        command = command,
        state = ContState.NONE,
        workMode = ContWorkMode.TEST,
        studRequest = ContStudent(
            id = stub.id,
            fio = "Иванов",
        ),
    )
    ctx.addTestPrincipal()
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(ContState.FAILING, ctx.state)
    assertEquals("Иванов", ctx.studValidated.fio)
}

fun validationFioTrim(command: ContCommand, processor: ContStudentProcessor) = runTest {
    val ctx = ContContext(
        command = command,
        state = ContState.NONE,
        workMode = ContWorkMode.TEST,
        studRequest = ContStudent(
            id = stub.id,
            fio = " \n\t Иванов \t\n ",
        ),
    )
    ctx.addTestPrincipal()
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(ContState.FAILING, ctx.state)
    assertEquals("Иванов", ctx.studValidated.fio)
}

fun validationFioEmpty(command: ContCommand, processor: ContStudentProcessor) = runTest {
    val ctx = ContContext(
        command = command,
        state = ContState.NONE,
        workMode = ContWorkMode.TEST,
        studRequest = ContStudent(
            id = stub.id,
            fio = "",
        ),
    )
    ctx.addTestPrincipal()
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(ContState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("fio", error?.field)
    assertContains(error?.message ?: "", "fio")
}

fun validationFioSymbols(command: ContCommand, processor: ContStudentProcessor) = runTest {
    val ctx = ContContext(
        command = command,
        state = ContState.NONE,
        workMode = ContWorkMode.TEST,
        studRequest = ContStudent(
            id = ContStudentId(123),
            fio = "!@#$%^&*(),.{}",
        ),
    )
    ctx.addTestPrincipal()
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(ContState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("fio", error?.field)
    assertContains(error?.message ?: "", "fio")
}
