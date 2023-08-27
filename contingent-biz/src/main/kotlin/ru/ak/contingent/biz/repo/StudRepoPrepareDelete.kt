package ru.ak.contingent.biz.repo

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.processor

fun ICorChainDsl<ContContext>.repoPrepareDelete(title: String) = processor {
    this.title = title
    description = """
        Готовим данные к удалению из БД
    """.trimIndent()
    on { state == ContState.RUNNING }
    handle {
        studRepoPrepare = studValidated.copy()
    }
}
