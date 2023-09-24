package ru.ak.contingent.biz.permissions

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContState
import ru.ak.contingent.common.permissions.resolveChainPermissions
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.processor

fun ICorChainDsl<ContContext>.chainPermissions(title: String) = processor {
    this.title = title
    description = "Вычисление прав доступа для групп пользователей"

    on { state == ContState.RUNNING }

    handle {
        permissionsChain.addAll(resolveChainPermissions(principal.groups))
        println("PRINCIPAL: $principal")
        println("PERMISSIONS: $permissionsChain")
    }
}
