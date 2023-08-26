package ru.ak.contingent.blackbox.test.action

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import ru.ak.contingent.api.models.StudentCreateObject
import ru.ak.contingent.api.models.StudentCreateRequest
import ru.ak.contingent.api.models.StudentCreateResponse
import ru.ak.contingent.api.models.StudentResponseObject
import ru.ak.contingent.blackbox.fixture.client.Client

suspend fun Client.createStudent(student: StudentCreateObject = someCreateStudent): StudentResponseObject = createStudent(student) {
    it should haveSuccessResult
    it.student shouldNotBe null
    it.student?.apply {
        fio shouldBe student.fio
//        description shouldBe ad.description
//        adType shouldBe ad.adType
//        visibility shouldBe ad.visibility
    }
    it.student!!
}

suspend fun <T> Client.createStudent(student: StudentCreateObject = someCreateStudent, block: (StudentCreateResponse) -> T): T =
    withClue("createStudent: $student") {
        val response = sendAndReceive(
            "student/create", StudentCreateRequest(
                requestType = "create",
                debug = debug,
                student = student
            )
        ) as StudentCreateResponse

        response.asClue(block)
    }
