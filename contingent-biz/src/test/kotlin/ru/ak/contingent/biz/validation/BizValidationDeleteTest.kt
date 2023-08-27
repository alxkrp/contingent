package ru.ak.contingent.biz.validation

import ru.ak.contingent.backend.repository.inmemory.StudRepoStub
import ru.ak.contingent.biz.ContStudentProcessor
import ru.ak.contingent.common.ContCorSettings
import ru.ak.contingent.common.models.ContCommand
import kotlin.test.Test

class BizValidationDeleteTest {

    private val command = ContCommand.DELETE
    private val processor = ContStudentProcessor(ContCorSettings(repoTest = StudRepoStub()))

    @Test fun correctId() = validationIdCorrect(command, processor)
}

