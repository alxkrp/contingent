package ru.ak.contingent.mappers

import ru.ak.contingent.api.models.*
import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.*
import ru.ak.contingent.mappers.exceptions.UnknownContCommand

fun ContContext.toTransportStudent(): IResponse = when (val cmd = command) {
    ContCommand.CREATE -> toTransportCreate()
    ContCommand.READ -> toTransportRead()
    ContCommand.UPDATE -> toTransportUpdate()
    ContCommand.DELETE -> toTransportDelete()
    ContCommand.SEARCH -> toTransportSearch()
    ContCommand.NONE -> throw UnknownContCommand(cmd)
}

fun ContContext.toTransportCreate() = StudentCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ContState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    student = studResponse.toTransportStudent()
)

fun ContContext.toTransportRead() = StudentReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ContState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    student = studResponse.toTransportStudent()
)

fun ContContext.toTransportUpdate() = StudentUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ContState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    student = studResponse.toTransportStudent()
)

fun ContContext.toTransportDelete() = StudentDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ContState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    student = studResponse.toTransportStudent()
)

fun ContContext.toTransportSearch() = StudentSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ContState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    students = studsResponse.toTransportStudent()
)

fun List<ContStudent>.toTransportStudent(): List<StudentResponseObject>? = this
    .map { it.toTransportStudent() }
    .toList()
    .takeIf { it.isNotEmpty() }

fun ContContext.toTransportInit() = StudentInitResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
)

private fun ContStudent.toTransportStudent(): StudentResponseObject = StudentResponseObject(
    id = id.takeIf { it != ContStudentId.NONE }?.asInt(),
    fio = fio.takeIf { it.isNotBlank() },
    sex = sex.takeIf { it != ContStudentSex.NONE }?.toTransportSex(),
    semester = semester.takeIf { it != 0 },
    eduYear = eduYear.takeIf { it != 0 },
    specialityId = specialityId.takeIf { it != 0 },
    facultyId = facultyId.takeIf { it != 0 },
    groupNum = groupNum.takeIf { it.isNotBlank() },
)

private fun ContStudentSex.toTransportSex(): Sex? = when (this) {
    ContStudentSex.M -> Sex.M
    ContStudentSex.W -> Sex.W
    ContStudentSex.NONE -> null
}

private fun List<ContError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportStudent() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun ContError.toTransportStudent() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)
