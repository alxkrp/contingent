package ru.ak.contingent.biz.validation

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.helpers.errorValidation
import ru.ak.contingent.common.helpers.fail
import ru.ak.contingent.common.models.ContStudentId
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.processor

fun ICorChainDsl<ContContext>.validateIdProperlyValue(title: String) = processor {
    this.title = title

    on { studValidating.id != ContStudentId.NONE && studValidating.id.asInt() <= 0 }
    handle {
        val id = studValidating.id.asInt();
        fail(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value $id must be greater 0"
            )
        )
    }
}
