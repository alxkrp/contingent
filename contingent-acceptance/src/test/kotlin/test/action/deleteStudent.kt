package ru.ak.contingent.blackbox.test.action

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import ru.ak.contingent.api.models.ContingentDebug
import ru.ak.contingent.api.models.StudentDeleteObject
import ru.ak.contingent.api.models.StudentDeleteRequest
import ru.ak.contingent.api.models.StudentDeleteResponse
import ru.ak.contingent.blackbox.fixture.client.Client

suspend fun Client.deleteStudent(id: Int?, lock: String?, mode: ContingentDebug = debug) {
    withClue("deleteStudent: $id, lock: $lock") {
        id should beValidId
        lock should beValidLock

        val response = sendAndReceive(
            "student/delete",
            StudentDeleteRequest(
                requestType = "delete",
                debug = mode,
                student = StudentDeleteObject(id = id, lock = lock)
            )
        ) as StudentDeleteResponse

        response.asClue {
            response should haveSuccessResult
            response.student shouldNotBe null
            response.student?.id shouldBe id
        }
    }
}