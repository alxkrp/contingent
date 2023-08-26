package ru.ak.contingent.blackbox.test.action

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldNotBe
import ru.ak.contingent.api.models.StudentReadObject
import ru.ak.contingent.api.models.StudentReadRequest
import ru.ak.contingent.api.models.StudentReadResponse
import ru.ak.contingent.api.models.StudentResponseObject
import ru.ak.contingent.blackbox.fixture.client.Client

suspend fun Client.readStudent(id: Int?): StudentResponseObject = readStudent(id) {
    it should haveSuccessResult
    it.student shouldNotBe null
    it.student!!
}

suspend fun <T> Client.readStudent(id: Int?, block: (StudentReadResponse) -> T): T =
    withClue("readStudent: $id") {
        id should beValidId

        val response = sendAndReceive(
            "student/read",
            StudentReadRequest(
                requestType = "read",
                debug = debug,
                student = StudentReadObject(id = id)
            )
        ) as StudentReadResponse

        response.asClue(block)
    }
