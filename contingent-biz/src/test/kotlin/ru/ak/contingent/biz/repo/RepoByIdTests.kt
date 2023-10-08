package ru.ak.contingent.biz.repo

import kotlinx.coroutines.test.runTest
import ru.ak.contingent.backend.repo.tests.StudRepositoryMock
import ru.ak.contingent.biz.ContStudentProcessor
import ru.ak.contingent.biz.addTestPrincipal
import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.ContCorSettings
import ru.ak.contingent.common.models.*
import ru.ak.contingent.common.repo.DbStudResponse
import kotlin.test.assertEquals

private val initAd = ContStudent(
    id = ContStudentId(123),
    fio = "abc",
//    description = "abc",
//    adType = MkplDealSide.DEMAND,
//    visibility = MkplVisibility.VISIBLE_PUBLIC,
)
private val repo = StudRepositoryMock(
        invokeReadStudent = {
            if (it.id == initAd.id) {
                DbStudResponse(
                    isSuccess = true,
                    data = initAd,
                )
            } else DbStudResponse(
                isSuccess = false,
                data = null,
                errors = listOf(ContError(message = "Not found", field = "id"))
            )
        }
    )
private val settings by lazy {
    ContCorSettings(
        repoTest = repo
    )
}
private val processor by lazy { ContStudentProcessor(settings) }

fun repoNotFoundTest(command: ContCommand) = runTest {
    val ctx = ContContext(
        command = command,
        state = ContState.NONE,
        workMode = ContWorkMode.TEST,
        studRequest = ContStudent(
            id = ContStudentId(12345),
            fio = "xyz",
//            description = "xyz",
//            adType = MkplDealSide.DEMAND,
//            visibility = MkplVisibility.VISIBLE_TO_GROUP,
            lock = ContStudentLock("123-234-abc-ABC"),
        ),
    )
    ctx.addTestPrincipal()
    processor.exec(ctx)
    assertEquals(ContState.FAILING, ctx.state)
    assertEquals(ContStudent(), ctx.studResponse)
    assertEquals(1, ctx.errors.size)
    assertEquals("id", ctx.errors.first().field)
}
