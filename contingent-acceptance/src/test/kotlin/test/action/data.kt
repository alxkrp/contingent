package ru.ak.contingent.blackbox.test.action

import ru.ak.contingent.api.models.*

val debug = ContingentDebug(mode = ContingentRequestDebugMode.STUB, stub = ContingentRequestDebugStubs.SUCCESS)
val prod = ContingentDebug(mode = ContingentRequestDebugMode.PROD)

val someCreateStudent = StudentCreateObject(
    fio = "Иванов Иван Иванович",
    sex = Sex.M,
    semester = 2,
    eduYear = 2023,
    specialityId = 3,
    facultyId = 2,
    groupNum = "102-л"
)
