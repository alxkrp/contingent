package ru.ak.contingent.biz.validation

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotEquals
import ru.ak.contingent.biz.ContStudentProcessor
import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.*

fun validationIdCorrect(command: ContCommand, processor: ContStudentProcessor) = runTest {
    val ctx = ContContext(
        command = command,
        state = ContState.NONE,
        workMode = ContWorkMode.TEST,
        studRequest = ContStudent(
            id = ContStudentId(1),
            fio = "Иванов",
        )
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(ContState.FAILING, ctx.state)
}
