package ru.ak.contingent.common.models

data class ContStudent(
    var id: ContStudentId = ContStudentId.NONE,
    var fio: String = "",
    var sex: ContStudentSex = ContStudentSex.NONE,
    var semester: Int = 0,
    var eduYear: Int = 0,
    var specialityId: Int = 0,
    var facultyId: Int = 0,
    var groupNum: String = "",
)
