package ru.ak.contingent.blackbox.test.action

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldNotBe
import ru.ak.contingent.api.models.*
import ru.ak.contingent.blackbox.fixture.client.Client

suspend fun Client.readStudent(id: Int?, mode: ContingentDebug = debug): StudentResponseObject = readStudent(id, mode) {
    it should haveSuccessResult
    it.student shouldNotBe null
    it.student!!
}

suspend fun <T> Client.readStudent(id: Int?, mode: ContingentDebug = debug, block: (StudentReadResponse) -> T): T =
    withClue("readStudent: $id") {
        id should beValidId

        val response = sendAndReceive(
            "student/read",
            StudentReadRequest(
                requestType = "read",
                debug = mode,
                student = StudentReadObject(id = id)
            )
        ) as StudentReadResponse

        response.asClue(block)
    }
