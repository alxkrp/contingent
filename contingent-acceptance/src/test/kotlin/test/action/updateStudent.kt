package ru.ak.contingent.blackbox.test.action

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import ru.ak.contingent.api.models.*
import ru.ak.contingent.blackbox.fixture.client.Client

suspend fun Client.updateStudent(id: Int?, lock: String?, student: StudentUpdateObject, mode: ContingentDebug = debug): StudentResponseObject =
    updateStudent(id, lock, student, mode) {
        it should haveSuccessResult
        it.student shouldNotBe null
        it.student?.apply {
            if (student.fio != null)
                fio shouldBe student.fio
            if (student.sex != null)
                sex shouldBe student.sex
            if (student.semester != null)
                semester shouldBe student.semester
            if (student.eduYear != null)
                eduYear shouldBe student.eduYear
            if (student.specialityId != null)
                specialityId shouldBe student.specialityId
            if (student.facultyId != null)
                facultyId shouldBe student.facultyId
            if (student.groupNum != null)
                groupNum shouldBe student.groupNum
        }
        it.student!!
    }

suspend fun <T> Client.updateStudent(id: Int?, lock: String?, student: StudentUpdateObject, mode: ContingentDebug = debug, block: (StudentUpdateResponse) -> T): T =
    withClue("updatedStudent: $id, lock: $lock, set: $student") {
        id should beValidId
        lock should beValidLock

        val response = sendAndReceive(
            "student/update", StudentUpdateRequest(
                requestType = "update",
                debug = mode,
                student = student.copy(id = id, lock = lock)
            )
        ) as StudentUpdateResponse

        response.asClue(block)
    }
