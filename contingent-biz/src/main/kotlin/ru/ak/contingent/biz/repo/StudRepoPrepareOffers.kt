package ru.ak.contingent.biz.repo

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.processor

fun ICorChainDsl<ContContext>.repoPrepareOffers(title: String) = processor {
    this.title = title
    description = "Готовим данные к поиску предложений в БД"
    on { state == ContState.RUNNING }
    handle {
        studRepoPrepare = studRepoRead.copy()
        studRepoDone = studRepoRead.copy()
    }
}
