package ru.ak.contingent.biz.validation

import ru.ak.contingent.backend.repository.inmemory.StudRepoStub
import ru.ak.contingent.biz.ContStudentProcessor
import ru.ak.contingent.common.ContCorSettings
import ru.ak.contingent.common.models.ContCommand
import kotlin.test.Test

class BizValidationUpdateTest {

    private val command = ContCommand.UPDATE
    private val processor = ContStudentProcessor(ContCorSettings(repoTest = StudRepoStub()))

    @Test fun correctFio() = validationFioCorrect(command, processor)
    @Test fun trimFio() = validationFioTrim(command, processor)
    @Test fun emptyFio() = validationFioEmpty(command, processor)
    @Test fun badSymbolsFio() = validationFioSymbols(command, processor)

    @Test fun correctId() = validationIdCorrect(command, processor)

}

