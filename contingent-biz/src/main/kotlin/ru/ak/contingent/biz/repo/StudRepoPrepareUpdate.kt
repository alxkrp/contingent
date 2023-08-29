package ru.ak.contingent.biz.repo

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContState
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
            this.sex = studValidated.sex
            this.semester = studValidated.semester
            this.specialityId = studValidated.specialityId
            this.facultyId = studValidated.specialityId
            this.groupNum = studValidated.groupNum
        }
    }
}
