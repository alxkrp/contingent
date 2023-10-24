package ru.ak.contingent.biz.repo

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.common.models.ContStudentSex
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.processor

fun ICorChainDsl<ContContext>.repoPrepareUpdate(title: String) = processor {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == ContState.RUNNING }
    handle {
        studRepoPrepare = studRepoRead.copy().apply {
            this.fio = studValidated.fio
            if (studValidated.sex != ContStudentSex.NONE) {
                this.sex = studValidated.sex
            }
            if (studValidated.semester != 0) {
                this.semester = studValidated.semester
            }
            if (studValidated.specialityId != 0) {
                this.specialityId = studValidated.specialityId
            }
            if (studValidated.specialityId != 0) {
                this.facultyId = studValidated.specialityId
            }
            if (studValidated.groupNum.isNotBlank()) {
                this.groupNum = studValidated.groupNum
            }
            this.lock = studValidated.lock
        }
    }
}
