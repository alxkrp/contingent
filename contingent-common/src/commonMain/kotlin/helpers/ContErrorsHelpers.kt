package ru.ak.contingent.common.helpers

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContError
import ru.ak.contingent.common.models.ContState

fun Throwable.asContError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = ContError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun ContContext.addError(vararg error: ContError) = errors.addAll(error)

fun ContContext.fail(error: ContError) {
    addError(error)
    state = ContState.FAILING
}