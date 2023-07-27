package ru.ak.contingent.biz.groups

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.common.models.ContWorkMode
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.chain

fun ICorChainDsl<ContContext>.stubs(title: String, block: ICorChainDsl<ContContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == ContWorkMode.STUB && state == ContState.RUNNING }
}
