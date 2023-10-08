package ru.ak.contingent.biz

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContUserId
import ru.ak.contingent.common.permissions.ContPrincipalModel
import ru.ak.contingent.common.permissions.ContUserGroups

fun ContContext.addTestPrincipal(userId: ContUserId = ContUserId("111")) {
    principal = ContPrincipalModel(
        id = userId,
        groups = setOf(
            ContUserGroups.USER,
            ContUserGroups.TEST,
        )
    )
}
