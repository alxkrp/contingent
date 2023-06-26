package ru.ak.contingent.biz

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.models.ContCommand
import ru.ak.contingent.common.models.ContWorkMode
import ru.ak.contingent.stubs.ContStudentStub

class ContStudentProcessor {
    suspend fun exec(ctx: ContContext) {
        // TODO: Rewrite temporary stub solution with BIZ
        require(ctx.workMode == ContWorkMode.STUB) {
            "Currently working only in STUB mode."
        }

        when (ctx.command) {
            ContCommand.SEARCH -> {
                ctx.studsResponse.addAll(ContStudentStub.prepareSearchList("Иванов"))
            }
            else -> {
                ctx.studResponse = ContStudentStub.get()
            }
        }
    }
}
