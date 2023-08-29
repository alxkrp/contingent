package ru.ak.contingent.common

import ru.ak.contingent.common.repo.IStudRepository
import ru.ak.contingent.logging.ContLoggerProvider

data class ContCorSettings(
    val loggerProvider: ContLoggerProvider = ContLoggerProvider(),
    val repoStub: IStudRepository = IStudRepository.NONE,
    val repoTest: IStudRepository = IStudRepository.NONE,
    val repoProd: IStudRepository = IStudRepository.NONE,
) {
    companion object {
        val NONE = ContCorSettings()
    }
}
