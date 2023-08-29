package ru.ak.contingent.biz.repo

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.common.repo.DbStudRequest
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.processor

fun ICorChainDsl<ContContext>.repoCreate(title: String) = processor {
    this.title = title
    description = "Добавление студента в БД"
    on { state == ContState.RUNNING }
    handle {
        val request = DbStudRequest(studRepoPrepare)
        val result = studRepo.createStud(request)
        val resultStudent = result.data
        if (result.isSuccess && resultStudent != null) {
            studRepoDone = resultStudent
        } else {
            state = ContState.FAILING
            errors.addAll(result.errors)
        }
    }
}
