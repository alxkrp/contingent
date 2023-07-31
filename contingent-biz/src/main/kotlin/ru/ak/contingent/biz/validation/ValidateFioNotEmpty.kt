package ru.otus.otuskotlin.marketplace.biz.validation

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.helpers.errorValidation
import ru.ak.contingent.common.helpers.fail
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.processor

fun ICorChainDsl<ContContext>.validateFioNotEmpty(title: String) = processor {
    this.title = title
    on { studValidating.fio.isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "fio",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
