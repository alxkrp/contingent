package ru.ak.contingent.biz.permissions

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.helpers.fail
import ru.ak.contingent.common.models.ContError
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.common.permissions.checkPermitted
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.chain
import ru.ak.contingent.cor.processor

fun ICorChainDsl<ContContext>.accessValidation(title: String) = chain {
    this.title = title
    description = "Вычисление прав доступа по группе принципала и таблице прав доступа"
    on { state == ContState.RUNNING }
    processor("Вычисление доступа к студенту") {
        permitted = checkPermitted(command, principal, permissionsChain)
    }
    processor {
        this.title = "Валидация прав доступа"
        description = "Проверка наличия прав для выполнения операции"
        on { !permitted }
        handle {
            fail(ContError(message = "У пользователя нет прав на выполнение данной операции"))
        }
    }
}

