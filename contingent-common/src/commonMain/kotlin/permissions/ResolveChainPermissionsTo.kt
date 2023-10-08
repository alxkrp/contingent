package ru.ak.contingent.common.permissions

fun resolveChainPermissions(
    groups: Iterable<ContUserGroups>,
) = mutableSetOf<ContUserPermissions>()
    .apply {
        addAll(groups.flatMap { groupPermissionsAdmits[it] ?: emptySet() })
    }
    .toSet()

private val groupPermissionsAdmits = mapOf(
    ContUserGroups.USER to setOf(
        ContUserPermissions.READ,
        ContUserPermissions.SEARCH,
    ),
    ContUserGroups.ADMIN to setOf(
        ContUserPermissions.CREATE,
        ContUserPermissions.READ,
        ContUserPermissions.UPDATE,
        ContUserPermissions.DELETE,
        ContUserPermissions.SEARCH,
    ),
    ContUserGroups.TEST to setOf(
        ContUserPermissions.CREATE,
        ContUserPermissions.READ,
        ContUserPermissions.UPDATE,
        ContUserPermissions.DELETE,
        ContUserPermissions.SEARCH,
    ),
)
