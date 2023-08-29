package ru.ak.contingent.biz.processors

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.common.models.ContStudentId
import ru.ak.contingent.common.models.ContStudentSex
import ru.ak.contingent.common.stubs.ContStubs
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.processor
import ru.ak.contingent.stubs.ContStudentStub

fun ICorChainDsl<ContContext>.stubUpdateSuccess(title: String) = processor {
    this.title = title
    on { stubCase == ContStubs.SUCCESS && state == ContState.RUNNING }
    handle {
        state = ContState.FINISHING
        val stub = ContStudentStub.prepareResult {
            studRequest.id.takeIf { it != ContStudentId.NONE }?.also { this.id = it }
            studRequest.fio.takeIf { it.isNotBlank() }?.also { this.fio = it }
            studRequest.sex.takeIf { it != ContStudentSex.NONE }?.also { this.sex = it }
            studRequest.semester.takeIf { it > 0 }?.also { this.semester = it }
            studRequest.eduYear.takeIf { it > 0 }?.also { this.eduYear = it }
            studRequest.specialityId.takeIf { it > 0 }?.also { this.specialityId = it }
            studRequest.facultyId.takeIf { it > 0 }?.also { this.facultyId = it }
            studRequest.groupNum.takeIf { it.isNotBlank() }?.also { this.groupNum = it }
        }
        studResponse = stub
    }
}
