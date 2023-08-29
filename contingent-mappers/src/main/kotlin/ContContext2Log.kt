package ru.ak.contingent.mappers

import kotlinx.datetime.Clock
import ru.ak.contingent.api.logs.models.*
import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.*

fun ContContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "contingent",
    student = toContLog(),
    errors = errors.map { it.toLog() },
)

fun ContContext.toContLog(): StudentLogModel? {
    val adNone = ContStudent()
    return StudentLogModel(
        requestId = requestId.takeIf { it != ContRequestId.NONE }?.asString(),
        requestStud = studRequest.takeIf { it != adNone }?.toLog(),
        responseStud = studResponse.takeIf { it != adNone }?.toLog(),
        responseStuds = studsResponse.takeIf { it.isNotEmpty() }?.filter { it != adNone }?.map { it.toLog() },
        requestFilter = studFilterRequest.takeIf { it != ContStudentFilter() }?.toLog(),
    ).takeIf { it != StudentLogModel() }
}

private fun ContStudentFilter.toLog() = StudentFilterLog(
    searchString = searchString.takeIf { it.isNotBlank() },
)

fun ContError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)

fun ContStudent.toLog() = StudentLog(
    id = id.takeIf { it != ContStudentId.NONE }?.asInt(),
    fio = fio.takeIf { it.isNotBlank() },
    sex = sex.takeIf { it != ContStudentSex.NONE }?.name,
    semester = semester.takeIf { it != 0 },
    eduYear = eduYear.takeIf { it != 0 },
    specialityId = specialityId.takeIf { it != 0 },
    facultyId = facultyId.takeIf { it != 0 },
    groupNum = groupNum.takeIf { it.isNotBlank() },
)
