package ru.ak.contingent.biz.processors

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.common.stubs.ContStubs
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.processor
import ru.ak.contingent.stubs.ContStudentStub

fun ICorChainDsl<ContContext>.stubSearchSuccess(title: String) = processor {
    this.title = title
    on { stubCase == ContStubs.SUCCESS && state == ContState.RUNNING }
    handle {
        state = ContState.FINISHING
        studsResponse.addAll(ContStudentStub.prepareSearchList(studFilterRequest.searchString))
    }
}
