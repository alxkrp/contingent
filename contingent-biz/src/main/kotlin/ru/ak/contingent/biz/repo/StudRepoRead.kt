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
        val resultStudent = result.data
        if (result.isSuccess && resultStudent != null) {
            studRepoRead = resultStudent
        } else {
            state = ContState.FAILING
            errors.addAll(result.errors)
        }
    }
}
