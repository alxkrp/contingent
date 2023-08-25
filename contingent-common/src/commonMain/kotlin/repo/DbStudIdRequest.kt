package ru.ak.contingent.common.repo

import ru.ak.contingent.common.models.ContStudent
import ru.ak.contingent.common.models.ContStudentId
import ru.ak.contingent.common.models.ContStudentLock

data class DbStudIdRequest(
    val id: ContStudentId,
    val lock: ContStudentLock = ContStudentLock.NONE,
) {
    constructor(stud: ContStudent): this(stud.id, stud.lock)
}
