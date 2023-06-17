package ru.ak.contingent.common

import kotlinx.datetime.Instant
import ru.ak.contingent.common.models.*
import ru.ak.contingent.common.stubs.ContStubs

data class ContContext(
    var command: ContCommand = ContCommand.NONE,
    var state: ContState = ContState.NONE,
    val errors: MutableList<ContError> = mutableListOf(),

    var workMode: ContWorkMode = ContWorkMode.PROD,
    var stubCase: ContStubs = ContStubs.NONE,

    var requestId: ContRequestId = ContRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var studRequest: ContStudent = ContStudent(),
    var studFilterRequest: ContStudentFilter = ContStudentFilter(),
    var studResponse: ContStudent = ContStudent(),
    val studsResponse: MutableList<ContStudent> = mutableListOf(),
)
