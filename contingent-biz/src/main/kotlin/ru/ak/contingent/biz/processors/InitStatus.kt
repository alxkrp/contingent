package ru.ak.contingent.biz.processors

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.processor

fun ICorChainDsl<ContContext>.initStatus(title: String) = processor() {
    this.title = title
    on { state == ContState.NONE }
    handle { state = ContState.RUNNING }
}
