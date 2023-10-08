package ru.ak.contingent.common.permissions

import ru.ak.contingent.common.models.ContUserId

data class ContPrincipalModel(
    val id: ContUserId = ContUserId.NONE,
    val login: String = "",
    val groups: Set<ContUserGroups> = emptySet()
) {
    companion object {
        val NONE = ContPrincipalModel()
    }
}
