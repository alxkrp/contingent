package ru.ak.contingent.common.repo

import ru.ak.contingent.common.models.ContError
import ru.ak.contingent.common.models.ContStudent

data class DbStudsResponse(
    override val data: List<ContStudent>?,
    override val isSuccess: Boolean,
    override val errors: List<ContError> = emptyList(),
): IDbResponse<List<ContStudent>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbStudsResponse(emptyList(), true)
        fun success(result: List<ContStudent>) = DbStudsResponse(result, true)
        fun error(errors: List<ContError>) = DbStudsResponse(null, false, errors)
        fun error(error: ContError) = DbStudsResponse(null, false, listOf(error))
    }
}
