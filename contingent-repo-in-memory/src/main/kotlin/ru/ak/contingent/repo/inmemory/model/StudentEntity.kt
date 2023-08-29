package ru.ak.contingent.backend.repo.inmemory.model

import ru.ak.contingent.common.models.ContStudent
import ru.ak.contingent.common.models.ContStudentId
import ru.ak.contingent.common.models.ContStudentLock
import ru.ak.contingent.common.models.ContStudentSex

data class StudentEntity(
    var id: Int? = null,
    var fio: String? = null,
    var sex: String? = null,
    var semester: Int? = null,
    var eduYear: Int? = null,
    var specialityId: Int? = null,
    var facultyId: Int? = null,
    var groupNum:String? = null,
    var lock: String? = null,
) {
    constructor(model: ContStudent): this(
        id = model.id.asInt().takeIf { it != null },
        fio = model.fio.takeIf { it.isNotBlank() },
        sex = model.sex.takeIf { it != ContStudentSex.NONE }?.name,
        semester = model.semester.takeIf { it > 0 },
        eduYear = model.eduYear.takeIf { it > 0 },
        specialityId = model.specialityId.takeIf { it > 0 },
        facultyId = model.facultyId.takeIf { it > 0 },
        groupNum = model.groupNum.takeIf { it.isNotBlank() },
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = ContStudent(
        id = id?.let { ContStudentId(it) }?: ContStudentId.NONE,
        fio = fio?: "",
        sex = sex?.let{ ContStudentSex.valueOf(it) }?: ContStudentSex.NONE,
        semester = semester?: 0,
        eduYear = eduYear?: 0,
        specialityId = specialityId?: 0,
        facultyId = facultyId?: 0,
        groupNum = groupNum?: "",
        lock = lock?.let { ContStudentLock(it) } ?: ContStudentLock.NONE,
    )
}
