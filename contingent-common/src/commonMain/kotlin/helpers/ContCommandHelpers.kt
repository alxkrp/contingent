package ru.ak.contingent.common.helpers

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContCommand

fun ContContext.isUpdatableCommand() =
    this.command in listOf(ContCommand.CREATE, ContCommand.UPDATE, ContCommand.DELETE)
