package ru.ak.contingent.biz.repo

import kotlinx.coroutines.test.runTest
import ru.ak.contingent.backend.repo.tests.StudRepositoryMock
import ru.ak.contingent.biz.ContStudentProcessor
import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.ContCorSettings
import ru.ak.contingent.common.models.*
import ru.ak.contingent.common.repo.DbStudResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BizRepoDeleteTest {

//    private val userId = MkplUserId("321")
    private val command = ContCommand.DELETE
    private val initStudent = ContStudent(
        id = ContStudentId(123),
        fio = "abc",
//        description = "abc",
//        ownerId = userId,
//        adType = MkplDealSide.DEMAND,
//        visibility = MkplVisibility.VISIBLE_PUBLIC,
        lock = ContStudentLock("123-234-abc-ABC"),
    )
    private val repo by lazy {
        StudRepositoryMock(
            invokeReadStudent = {
               DbStudResponse(
                   isSuccess = true,
                   data = initStudent,
               )
            },
            invokeDeleteStudent = {
                if (it.id == initStudent.id)
                    DbStudResponse(
                        isSuccess = true,
                        data = initStudent
                    )
                else DbStudResponse(isSuccess = false, data = null)
            }
        )
    }
    private val settings by lazy {
        ContCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { ContStudentProcessor(settings) }

    @Test
    fun repoDeleteSuccessTest() = runTest {
        val adToUpdate = ContStudent(
            id = ContStudentId(123),
            lock = ContStudentLock("123-234-abc-ABC"),
        )
        val ctx = ContContext(
            command = command,
            state = ContState.NONE,
            workMode = ContWorkMode.TEST,
            studRequest = adToUpdate,
        )
        processor.exec(ctx)
        assertEquals(ContState.FINISHING, ctx.state)
        assertTrue { ctx.errors.isEmpty() }
        assertEquals(initStudent.id, ctx.studResponse.id)
        assertEquals(initStudent.fio, ctx.studResponse.fio)
//        assertEquals(initAd.description, ctx.studResponse.description)
//        assertEquals(initAd.adType, ctx.studResponse.adType)
//        assertEquals(initAd.visibility, ctx.studResponse.visibility)
    }

    @Test
    fun repoDeleteNotFoundTest() = repoNotFoundTest(command)
}
