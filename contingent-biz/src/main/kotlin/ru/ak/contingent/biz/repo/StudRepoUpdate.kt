package ru.ak.contingent.biz.repo

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.common.repo.DbStudRequest
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.processor

fun ICorChainDsl<ContContext>.repoUpdate(title: String) = processor {
    this.title = title
    on { state == ContState.RUNNING }
    handle {
        val request = DbStudRequest(studRepoPrepare)
        val result = studRepo.updateStud(request)
        val resultAd = result.data
        if (result.isSuccess && resultAd != null) {
            studRepoDone = resultAd
        } else {
            state = ContState.FAILING
            errors.addAll(result.errors)
            studRepoDone
        }
    }
}
