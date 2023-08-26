package ru.ak.contingent.common.repo

import ru.ak.contingent.common.models.ContStudentSex

data class DbStudFilterRequest(
    val fioFilter: String = "",
    val sex: ContStudentSex,
)
