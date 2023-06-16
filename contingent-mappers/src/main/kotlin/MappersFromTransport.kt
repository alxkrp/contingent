package ru.ak.contingent.mappers

import ru.ak.contingent.api.models.*
import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.*
import ru.ak.contingent.common.stubs.ContStubs
import ru.ak.contingent.mappers.exceptions.UnknownRequestClass

fun ContContext.fromTransport(request: IRequest) = when (request) {
    is StudentCreateRequest -> fromTransport(request)
    is StudentReadRequest -> fromTransport(request)
    is StudentUpdateRequest -> fromTransport(request)
    is StudentDeleteRequest -> fromTransport(request)
    is StudentSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun Int?.toStudentId() = this?.let { ContStudentId(it) } ?: ContStudentId.NONE
private fun Int?.toStudentWithId() = ContStudent(id = this.toStudentId())
private fun IRequest?.requestId() = this?.requestId?.let { ContRequestId(it) } ?: ContRequestId.NONE

private fun ContingentDebug?.transportToWorkMode(): ContWorkMode = when (this?.mode) {
    ContingentRequestDebugMode.PROD -> ContWorkMode.PROD
    ContingentRequestDebugMode.TEST -> ContWorkMode.TEST
    ContingentRequestDebugMode.STUB -> ContWorkMode.STUB
    null -> ContWorkMode.PROD
}

private fun ContingentDebug?.transportToStubCase(): ContStubs = when (this?.stub) {
    ContingentRequestDebugStubs.SUCCESS -> ContStubs.SUCCESS
    ContingentRequestDebugStubs.NOT_FOUND -> ContStubs.NOT_FOUND
    ContingentRequestDebugStubs.BAD_ID -> ContStubs.BAD_ID
    ContingentRequestDebugStubs.BAD_FIO -> ContStubs.BAD_FIO
    ContingentRequestDebugStubs.BAD_SEX -> ContStubs.BAD_SEX
    ContingentRequestDebugStubs.BAD_SPECIALITY -> ContStubs.BAD_SPECIALITY
    ContingentRequestDebugStubs.BAD_FACULTY -> ContStubs.BAD_FACULTY
    ContingentRequestDebugStubs.CANNOT_DELETE -> ContStubs.CANNOT_DELETE
    ContingentRequestDebugStubs.BAD_SEARCH_STRING -> ContStubs.BAD_SEARCH_STRING
    null -> ContStubs.NONE
}

fun ContContext.fromTransport(request: StudentCreateRequest) {
    command = ContCommand.CREATE
    requestId = request.requestId()
    studRequest = request.student?.toInternal() ?: ContStudent()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ContContext.fromTransport(request: StudentReadRequest) {
    command = ContCommand.READ
    requestId = request.requestId()
    studRequest = request.student?.id.toStudentWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ContContext.fromTransport(request: StudentUpdateRequest) {
    command = ContCommand.UPDATE
    requestId = request.requestId()
    studRequest = request.student?.toInternal() ?: ContStudent()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ContContext.fromTransport(request: StudentDeleteRequest) {
    command = ContCommand.DELETE
    requestId = request.requestId()
    studRequest = request.student?.id.toStudentWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ContContext.fromTransport(request: StudentSearchRequest) {
    command = ContCommand.SEARCH
    requestId = request.requestId()
    studFilterRequest = request.studentFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun Sex?.fromTransport(): ContStudentSex = when (this) {
    Sex.M -> ContStudentSex.M
    Sex.W -> ContStudentSex.W
    null -> ContStudentSex.NONE
}

private fun StudentSearchFilter?.toInternal(): ContStudentFilter = ContStudentFilter(
    searchString = this?.searchString ?: ""
)

private fun StudentCreateObject.toInternal(): ContStudent = ContStudent(
    fio = this.fio ?: "",
    sex = this.sex.fromTransport(),
    semester = this.semester ?: 0,
    eduYear = this.eduYear ?: 0,
    specialityId = this.specialityId ?: 0,
    facultyId = this.facultyId ?: 0,
    groupNum = this.groupNum ?: "",
)

private fun StudentUpdateObject.toInternal(): ContStudent = ContStudent(
    id = this.id.toStudentId(),
    fio = this.fio ?: "",
    sex = this.sex.fromTransport(),
    semester = this.semester ?: 0,
    eduYear = this.eduYear ?: 0,
    specialityId = this.specialityId ?: 0,
    facultyId = this.facultyId ?: 0,
    groupNum = this.groupNum ?: "",
)
