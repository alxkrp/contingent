package ru.ak.contingent.common.models

data class ContStudentFilter(
    var searchString: String = "",
    var sex: ContStudentSex = ContStudentSex.NONE
)
