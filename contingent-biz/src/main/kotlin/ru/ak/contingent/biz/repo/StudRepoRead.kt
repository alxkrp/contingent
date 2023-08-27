package ru.ak.contingent.biz.repo

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.common.repo.DbStudIdRequest
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.processor

fun ICorChainDsl<ContContext>.repoRead(title: String) = processor {
    this.title = title
    description = "Чтение студента из БД"
    on { state == ContState.RUNNING }
    handle {
        val request = DbStudIdRequest(studValidated)
        val result = studRepo.readStud(request)
        val resultAd = result.data
        if (result.isSuccess && resultAd != null) {
            studRepoRead = resultAd
        } else {
            state = ContState.FAILING
            errors.addAll(result.errors)
        }
    }
}
