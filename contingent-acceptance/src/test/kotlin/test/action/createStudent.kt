package ru.ak.contingent.blackbox.test.action

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import ru.ak.contingent.api.models.*
import ru.ak.contingent.blackbox.fixture.client.Client

suspend fun Client.createStudent(student: StudentCreateObject = someCreateStudent, mode: ContingentDebug = debug): StudentResponseObject =
    createStudent(student, mode) {
        it should haveSuccessResult
        it.student shouldNotBe null
        it.student?.apply {
            fio shouldBe student.fio
            sex shouldBe student.sex
            semester shouldBe student.semester
            eduYear shouldBe student.eduYear
            specialityId shouldBe student.specialityId
            facultyId shouldBe student.facultyId
            groupNum shouldBe student.groupNum
        }
        it.student!!
    }

suspend fun <T> Client.createStudent(
    student: StudentCreateObject = someCreateStudent,
    mode: ContingentDebug = debug,
    block: (StudentCreateResponse) -> T
): T =
    withClue("createStudent: $student") {
        val response = sendAndReceive(
            "student/create", StudentCreateRequest(
                requestType = "create",
                debug = mode,
                student = student
            )
        ) as StudentCreateResponse

        response.asClue(block)
    }
