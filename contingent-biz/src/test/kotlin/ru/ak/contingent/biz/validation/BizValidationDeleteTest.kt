package ru.ak.contingent.biz.validation

import ru.ak.contingent.biz.ContStudentProcessor
import ru.ak.contingent.common.models.ContCommand
import kotlin.test.Test

class BizValidationDeleteTest {

    private val command = ContCommand.DELETE
    private val processor by lazy { ContStudentProcessor() }

    @Test fun correctId() = validationIdCorrect(command, processor)
}

