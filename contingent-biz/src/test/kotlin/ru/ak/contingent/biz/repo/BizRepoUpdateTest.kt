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

class BizRepoUpdateTest {

//    private val userId = ContUserId("321")
    private val command = ContCommand.UPDATE
    private val initAd = ContStudent(
        id = ContStudentId(123),
        fio = "abc",
//        description = "abc",
//        ownerId = userId,
//        adType = MkplDealSide.DEMAND,
//        visibility = MkplVisibility.VISIBLE_PUBLIC,
    )
    private val repo by lazy { StudRepositoryMock(
        invokeReadStudent = {
            DbStudResponse(
                isSuccess = true,
                data = initAd,
            )
        },
        invokeUpdateStudent = {
            DbStudResponse(
                isSuccess = true,
                data = ContStudent(
                    id = ContStudentId(123),
                    fio = "xyz",
//                    description = "xyz",
//                    adType = MkplDealSide.DEMAND,
//                    visibility = MkplVisibility.VISIBLE_TO_GROUP,
                )
            )
        }
    ) }
    private val settings by lazy {
        ContCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { ContStudentProcessor(settings) }

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val adToUpdate = ContStudent(
            id = ContStudentId(123),
            fio = "xyz",
//            description = "xyz",
//            adType = MkplDealSide.DEMAND,
//            visibility = MkplVisibility.VISIBLE_TO_GROUP,
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
        assertEquals(adToUpdate.id, ctx.studResponse.id)
        assertEquals(adToUpdate.fio, ctx.studResponse.fio)
//        assertEquals(adToUpdate.description, ctx.adResponse.description)
//        assertEquals(adToUpdate.adType, ctx.adResponse.adType)
//        assertEquals(adToUpdate.visibility, ctx.adResponse.visibility)
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(command)
}
