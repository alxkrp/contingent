package ru.ak.contingent.common.helpers

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.exceptions.RepoConcurrencyException
import ru.ak.contingent.common.models.ContError
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.common.models.ContStudentLock

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

fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: ContError.Level = ContError.Level.ERROR,
) = ContError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)

fun errorRepoConcurrency(
    expectedLock: ContStudentLock,
    actualLock: ContStudentLock?,
    exception: Exception? = null,
) = ContError(
    field = "lock",
    code = "concurrency",
    group = "repo",
    message = "The object has been changed concurrently by another user or process",
    exception = exception ?: RepoConcurrencyException(expectedLock, actualLock),
)

val errorNotFound = ContError(
    field = "id",
    message = "Not Found",
    code = "not-found"
)

val errorEmptyId = ContError(
    field = "id",
    message = "Id must not be null or blank"
)
