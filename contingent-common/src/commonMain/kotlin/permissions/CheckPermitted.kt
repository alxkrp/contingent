package ru.ak.contingent.common.permissions

import ru.ak.contingent.common.models.ContCommand

fun checkPermitted(
    command: ContCommand,
    principal: ContPrincipalModel,
    permissions: Iterable<ContUserPermissions>,
) =
    principal.groups.flatMap { group ->
        permissions.map { permission ->
            AccessTableConditions(
                command = command,
                group = group,
                permission = permission,
            )
        }
    }.any {
        accessTable[it] != null
    }

private data class AccessTableConditions(
    val command: ContCommand,
    val group: ContUserGroups,
    val permission: ContUserPermissions,
)

private val accessTable = mapOf(
    AccessTableConditions(
        command = ContCommand.READ,
        permission = ContUserPermissions.READ,
        group = ContUserGroups.USER,
    ) to true,

    AccessTableConditions(
        command = ContCommand.SEARCH,
        permission = ContUserPermissions.SEARCH,
        group = ContUserGroups.USER,
    ) to true,

    AccessTableConditions(
        command = ContCommand.CREATE,
        permission = ContUserPermissions.CREATE,
        group = ContUserGroups.ADMIN,
    ) to true,

    AccessTableConditions(
        command = ContCommand.READ,
        permission = ContUserPermissions.READ,
        group = ContUserGroups.ADMIN,
    ) to true,

    AccessTableConditions(
        command = ContCommand.UPDATE,
        permission = ContUserPermissions.UPDATE,
        group = ContUserGroups.ADMIN,
    ) to true,

    AccessTableConditions(
        command = ContCommand.DELETE,
        permission = ContUserPermissions.DELETE,
        group = ContUserGroups.ADMIN,
    ) to true,

    AccessTableConditions(
        command = ContCommand.SEARCH,
        permission = ContUserPermissions.SEARCH,
        group = ContUserGroups.ADMIN,
    ) to true,

    AccessTableConditions(
        command = ContCommand.CREATE,
        permission = ContUserPermissions.CREATE,
        group = ContUserGroups.TEST,
    ) to true,

    AccessTableConditions(
        command = ContCommand.READ,
        permission = ContUserPermissions.READ,
        group = ContUserGroups.TEST,
    ) to true,

    AccessTableConditions(
        command = ContCommand.UPDATE,
        permission = ContUserPermissions.UPDATE,
        group = ContUserGroups.TEST,
    ) to true,

    AccessTableConditions(
        command = ContCommand.DELETE,
        permission = ContUserPermissions.DELETE,
        group = ContUserGroups.TEST,
    ) to true,

    AccessTableConditions(
        command = ContCommand.SEARCH,
        permission = ContUserPermissions.SEARCH,
        group = ContUserGroups.TEST,
    ) to true,
)
