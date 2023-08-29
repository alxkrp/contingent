package ru.ak.contingent.biz.repo

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.processor

fun ICorChainDsl<ContContext>.repoPrepareCreate(title: String) = processor {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == ContState.RUNNING }
    handle {
        studRepoRead = studValidated.copy()
        studRepoPrepare = studRepoRead

    }
}
