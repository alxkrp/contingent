package ru.ak.contingent.stubs

import ru.ak.contingent.common.models.ContStudent
import ru.ak.contingent.common.models.ContStudentId
import ru.ak.contingent.stubs.ContStudStubPersons.STUDENT_IVAN

object ContStudentStub {
    fun get(): ContStudent = STUDENT_IVAN.copy()

    fun prepareResult(block: ContStudent.() -> Unit): ContStudent = get().apply(block)

    fun prepareSearchList(filter: String) = listOf(
        contStudent(1, filter),
        contStudent(2, filter),
        contStudent(3, filter),
    )

   private fun contStudent(id: Int, filter: String) =
       contStud(STUDENT_IVAN, id = id, filter = filter)

    private fun contStud(base: ContStudent, id: Int, filter: String) = base.copy(
        id = ContStudentId(id),
        fio = "$filter $id",
    )

}
