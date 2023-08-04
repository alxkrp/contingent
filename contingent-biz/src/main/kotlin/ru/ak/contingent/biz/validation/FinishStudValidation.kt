package ru.ak.contingent.biz.validation

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.processor

fun ICorChainDsl<ContContext>.finishStudValidation(title: String) = processor {
    this.title = title
    on { state == ContState.RUNNING }
    handle {
        studValidated = studValidating
    }
}

fun ICorChainDsl<ContContext>.finishStudFilterValidation(title: String) = processor {
    this.title = title
    on { state == ContState.RUNNING }
    handle {
        studFilterValidated = studFilterValidating
    }
}
