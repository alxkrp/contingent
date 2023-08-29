package ru.ak.contingent.springapp.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.ak.contingent.backend.repo.sql.RepoStudSQL
import ru.ak.contingent.backend.repo.sql.SqlProperties
import ru.ak.contingent.backend.repository.inmemory.StudRepoStub
import ru.ak.contingent.biz.ContStudentProcessor
import ru.ak.contingent.common.ContCorSettings
import ru.ak.contingent.common.repo.IStudRepository
import ru.ak.contingent.logging.ContLoggerProvider
import ru.ak.contingent.logging.contLoggerLogback
import ru.ak.contingent.repo.inmemory.StudRepoInMemory

@Configuration
@EnableConfigurationProperties(SqlPropertiesEx::class)
class AppConfig {
    @Bean
    fun loggerProvider(): ContLoggerProvider = ContLoggerProvider { contLoggerLogback(it) }

    @Bean
    fun processor(corSettings: ContCorSettings) = ContStudentProcessor(corSettings)

    @Bean
    fun corSettings(
        @Qualifier("prodRepository") prodRepository: IStudRepository?,
        @Qualifier("testRepository") testRepository: IStudRepository,
        @Qualifier("stubRepository") stubRepository: IStudRepository,
    ): ContCorSettings = ContCorSettings(
        loggerProvider = loggerProvider(),
        repoStub = stubRepository,
        repoTest = testRepository,
        repoProd = prodRepository ?: testRepository,
    )

    @Bean
    fun appSettings(corSettings: ContCorSettings, processor: ContStudentProcessor) = ContAppSettings(
        processor = processor,
        logger = loggerProvider(),
        corSettings = corSettings
    )

    @Bean(name = ["prodRepository"])
    @ConditionalOnProperty(value = ["prod-repository"], havingValue = "sql")
    fun prodRepository(sqlProperties: SqlProperties) = RepoStudSQL(sqlProperties)

    @Bean
    fun testRepository() = StudRepoInMemory()

    @Bean
    fun stubRepository() = StudRepoStub()
}