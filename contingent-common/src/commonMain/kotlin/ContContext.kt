package ru.ak.contingent.common

import kotlinx.datetime.Instant
import ru.ak.contingent.common.models.*
import ru.ak.contingent.common.repo.IStudRepository
import ru.ak.contingent.common.stubs.ContStubs

data class ContContext(
    var command: ContCommand = ContCommand.NONE,
    var state: ContState = ContState.NONE,
    val errors: MutableList<ContError> = mutableListOf(),
    var settings: ContCorSettings = ContCorSettings.NONE,

    var workMode: ContWorkMode = ContWorkMode.PROD,
    var stubCase: ContStubs = ContStubs.NONE,

    var studRepo: IStudRepository = IStudRepository.NONE,
    var studRepoRead: ContStudent = ContStudent(),
    var studRepoPrepare: ContStudent = ContStudent(),
    var studRepoDone: ContStudent = ContStudent(),
    var studsRepoDone: MutableList<ContStudent> = mutableListOf(),

    var requestId: ContRequestId = ContRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var studRequest: ContStudent = ContStudent(),
    var studFilterRequest: ContStudentFilter = ContStudentFilter(),

    var studValidating: ContStudent = ContStudent(),
    var studFilterValidating: ContStudentFilter = ContStudentFilter(),

    var studValidated: ContStudent = ContStudent(),
    var studFilterValidated: ContStudentFilter = ContStudentFilter(),

    var studResponse: ContStudent = ContStudent(),
    val studsResponse: MutableList<ContStudent> = mutableListOf(),
)
