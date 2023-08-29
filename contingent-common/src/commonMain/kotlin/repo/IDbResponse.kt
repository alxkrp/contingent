package ru.ak.contingent.common.repo

import ru.ak.contingent.common.models.ContError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<ContError>
}
