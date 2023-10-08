package ru.ak.contingent.springapp.base

import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import ru.ak.contingent.common.permissions.ContPrincipalModel
import ru.ak.contingent.common.permissions.ContUserGroups

fun Authentication?.toModel() = this?.run {
    val userName = if (principal is String) (principal as String) else (principal as User).username
    ContPrincipalModel(
        login = userName,
        groups = authorities
            ?.mapNotNull {
                when(it.authority) {
                    "ROLE_USER" -> ContUserGroups.USER
                    "ROLE_ADMIN" -> ContUserGroups.ADMIN
                    "ROLE_TEST" -> ContUserGroups.TEST
                    else -> null
                }}
            ?.toSet() ?: emptySet()
    )
} ?: ContPrincipalModel.NONE
