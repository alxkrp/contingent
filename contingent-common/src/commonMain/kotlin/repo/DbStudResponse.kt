package ru.ak.contingent.common.repo

import ru.ak.contingent.common.helpers.errorRepoConcurrency
import ru.ak.contingent.common.models.ContError
import ru.ak.contingent.common.models.ContStudent
import ru.ak.contingent.common.models.ContStudentLock
import ru.ak.contingent.common.helpers.errorEmptyId as contErrorEmptyId
import ru.ak.contingent.common.helpers.errorNotFound as contErrorNotFound


data class DbStudResponse(
    override val data: ContStudent?,
    override val isSuccess: Boolean,
    override val errors: List<ContError> = emptyList()
): IDbResponse<ContStudent> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbStudResponse(null, true)
        fun success(result: ContStudent) = DbStudResponse(result, true)
        fun error(errors: List<ContError>, data: ContStudent? = null) = DbStudResponse(data, false, errors)
        fun error(error: ContError, data: ContStudent? = null) = DbStudResponse(data, false, listOf(error))

        val errorEmptyId = error(contErrorEmptyId)

        fun errorConcurrent(lock: ContStudentLock, stud: ContStudent?) = error(
            errorRepoConcurrency(lock, stud?.lock?.let { ContStudentLock(it.asString()) }),
            stud
        )

        val errorNotFound = error(contErrorNotFound)
    }
}
