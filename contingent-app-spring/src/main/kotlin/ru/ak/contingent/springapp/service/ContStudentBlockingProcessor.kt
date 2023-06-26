package ru.ak.contingent.springapp.service

import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import ru.ak.contingent.biz.ContStudentProcessor
import ru.ak.contingent.common.ContContext

@Service
class ContStudentBlockingProcessor {
    private val processor = ContStudentProcessor()

    fun exec(ctx: ContContext) = runBlocking { processor.exec(ctx) }
}
