package ru.ak.contingent.mappers.exceptions

import ru.ak.contingent.common.models.ContCommand

class UnknownContCommand(command: ContCommand) : Throwable("Wrong command $command at mapping toTransport stage")
