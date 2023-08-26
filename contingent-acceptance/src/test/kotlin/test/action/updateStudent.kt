package ru.ak.contingent.blackbox.test.action

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import ru.ak.contingent.api.models.StudentResponseObject
import ru.ak.contingent.api.models.StudentUpdateObject
import ru.ak.contingent.api.models.StudentUpdateRequest
import ru.ak.contingent.api.models.StudentUpdateResponse
import ru.ak.contingent.blackbox.fixture.client.Client

suspend fun Client.updateStudent(id: Int?, lock: String?, student: StudentUpdateObject): StudentResponseObject =
    updateStudent(id, lock, student) {
        it should haveSuccessResult
        it.student shouldNotBe null
        it.student?.apply {
            if (student.fio != null)
                fio shouldBe student.fio
//            if (ad.description != null)
//                description shouldBe ad.description
//            if (ad.adType != null)
//                adType shouldBe ad.adType
//            if (ad.visibility != null)
//                visibility shouldBe ad.visibility
        }
        it.student!!
    }

suspend fun <T> Client.updateStudent(id: Int?, lock: String?, student: StudentUpdateObject, block: (StudentUpdateResponse) -> T): T =
    withClue("updatedStudent: $id, lock: $lock, set: $student") {
        id should beValidId
        lock should beValidLock

        val response = sendAndReceive(
            "student/update", StudentUpdateRequest(
                requestType = "update",
                debug = debug,
                student = student.copy(id = id, lock = lock)
            )
        ) as StudentUpdateResponse

        response.asClue(block)
    }
