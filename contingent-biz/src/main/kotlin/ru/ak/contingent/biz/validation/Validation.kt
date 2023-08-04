package ru.ak.contingent.biz.validation

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.chain

fun ICorChainDsl<ContContext>.validation(block: ICorChainDsl<ContContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == ContState.RUNNING }
}
