package ru.ak.contingent.backend.repo.tests

import ru.ak.contingent.common.models.ContStudent
import ru.ak.contingent.common.models.ContStudentId
import ru.ak.contingent.common.models.ContStudentLock

abstract class BaseInitStuds(val op: String): IInitObjects<ContStudent> {

    open val lockOld: ContStudentLock = ContStudentLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: ContStudentLock = ContStudentLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        id: Int,
        suf: String,
        lock: ContStudentLock = lockOld,
    ) = ContStudent(
        id = ContStudentId(id),
        fio = "$suf stub",
        lock = lock,
    )
}
