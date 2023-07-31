package ru.ak.contingent.biz.validation

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.helpers.errorValidation
import ru.ak.contingent.common.helpers.fail
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.processor

fun ICorChainDsl<ContContext>.validateFioHasContent(title: String) = processor {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { studValidating.fio.isNotEmpty() && !studValidating.fio.contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "fio",
                violationCode = "noContent",
                description = "field must contain letters"
            )
        )
    }
}
