package ru.ak.contingent.biz.repo

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.common.repo.DbStudFilterRequest
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.processor

fun ICorChainDsl<ContContext>.repoSearch(title: String) = processor {
    this.title = title
    description = "Поиск студентов в БД по фильтру"
    on { state == ContState.RUNNING }
    handle {
        val request = DbStudFilterRequest(
            fioFilter = studFilterValidated.searchString,
            sex = studFilterValidated.sex,
        )
        val result = studRepo.searchStud(request)
        val resultStudents = result.data
        if (result.isSuccess && resultStudents != null) {
            studsRepoDone = resultStudents.toMutableList()
        } else {
            state = ContState.FAILING
            errors.addAll(result.errors)
        }
    }
}
