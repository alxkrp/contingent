package ru.ak.contingent.springapp.config

import ru.ak.contingent.biz.ContStudentProcessor
import ru.ak.contingent.common.ContCorSettings
import ru.ak.contingent.logging.ContLoggerProvider

data class ContAppSettings(
    val appUrls: List<String> = emptyList(),
    val corSettings: ContCorSettings = ContCorSettings(),
    val processor: ContStudentProcessor = ContStudentProcessor(),
    val logger: ContLoggerProvider
)
