package ru.ak.contingent.blackbox.test.action

import ru.ak.contingent.api.models.ContingentDebug
import ru.ak.contingent.api.models.ContingentRequestDebugMode
import ru.ak.contingent.api.models.ContingentRequestDebugStubs
import ru.ak.contingent.api.models.StudentCreateObject

val debug = ContingentDebug(mode = ContingentRequestDebugMode.STUB, stub = ContingentRequestDebugStubs.SUCCESS)

val someCreateStudent = StudentCreateObject(
    fio = "Иванов Иван Иванович",
//    description = "Требуется болт 100x5 с шестигранной шляпкой",
//    adType = DealSide.DEMAND,
//    visibility = AdVisibility.PUBLIC
)
