package ru.ak.contingent.biz.groups

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContCommand
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.chain

fun ICorChainDsl<ContContext>.operation(title: String, command: ContCommand, block: ICorChainDsl<ContContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { this.command == command && state == ContState.RUNNING }
}
