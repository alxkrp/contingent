package ru.ak.contingent.stubs

import ru.ak.contingent.common.models.ContStudent
import ru.ak.contingent.common.models.ContStudentId
import ru.ak.contingent.common.models.ContStudentSex

object ContStudStubPersons {
    val STUDENT_IVAN: ContStudent
        get() = ContStudent(
            id = ContStudentId(777),
            fio = "Иванов Иван Иванович",
            sex = ContStudentSex.M,
            semester = 2,
            eduYear = 2023,
            specialityId = 3,
            facultyId = 2,
            groupNum = "102-л",
        )
}
