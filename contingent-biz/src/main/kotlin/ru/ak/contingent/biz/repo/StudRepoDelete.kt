package ru.ak.contingent.biz.repo

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.common.repo.DbStudIdRequest
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.processor

fun ICorChainDsl<ContContext>.repoDelete(title: String) = processor {
    this.title = title
    description = "Удаление студента из БД по ID"
    on { state == ContState.RUNNING }
    handle {
        val request = DbStudIdRequest(studRepoPrepare)
        val result = studRepo.deleteStud(request)
        if (!result.isSuccess) {
            state = ContState.FAILING
            errors.addAll(result.errors)
        }
        studRepoDone = studRepoRead
    }
}
