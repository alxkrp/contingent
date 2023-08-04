package ru.ak.contingent.springapp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.ak.contingent.biz.ContStudentProcessor
import ru.ak.contingent.logging.ContLoggerProvider
import ru.ak.contingent.logging.contLoggerLogback

@Configuration
class AppConfig {
    @Bean
    fun loggerProvider(): ContLoggerProvider = ContLoggerProvider { contLoggerLogback(it) }

    @Bean
    fun processor() = ContStudentProcessor()

    @Bean
    fun appSettings() = ContAppSettings(
        processor = processor(),
        logger = loggerProvider(),
    )
}
