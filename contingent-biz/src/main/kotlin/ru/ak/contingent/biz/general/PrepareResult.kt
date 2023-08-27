package ru.ak.contingent.biz.general

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.common.models.ContWorkMode
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.processor

fun ICorChainDsl<ContContext>.prepareResult(title: String) = processor {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != ContWorkMode.STUB }
    handle {
        studResponse = studRepoDone
        studsResponse = studsRepoDone
        state = when (val st = state) {
            ContState.RUNNING -> ContState.FINISHING
            else -> st
        }
    }
}
