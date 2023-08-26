package ru.ak.contingent.biz.general

import ru.ak.contingent.common.ContContext
import ru.ak.contingent.common.helpers.errorAdministration
import ru.ak.contingent.common.helpers.fail
import ru.ak.contingent.common.models.ContWorkMode
import ru.ak.contingent.common.repo.IStudRepository
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.processor

fun ICorChainDsl<ContContext>.initRepo(title: String) = processor {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от зпрошенного режима работы        
    """.trimIndent()
    handle {
        studRepo = when {
            workMode == ContWorkMode.TEST -> settings.repoTest
            workMode == ContWorkMode.STUB -> settings.repoStub
            else -> settings.repoProd
        }
        if (workMode != ContWorkMode.STUB && studRepo == IStudRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}
