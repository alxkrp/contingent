package ru.ak.contingent.backend.repo.sql

import com.benasher44.uuid.uuid4
import org.testcontainers.containers.PostgreSQLContainer
import ru.ak.contingent.common.models.ContStudent
import java.time.Duration

class PostgresContainer : PostgreSQLContainer<PostgresContainer>("postgres:13.2")

object SqlTestCompanion {
    private const val USER = "postgres"
    private const val PASS = "contingent-pass"
    private const val SCHEMA = "contingent"

    private val container by lazy {
        PostgresContainer().apply {
            withUsername(USER)
            withPassword(PASS)
            withDatabaseName(SCHEMA)
            withStartupTimeout(Duration.ofSeconds(300L))
            start()
        }
    }

    private val url: String by lazy { container.jdbcUrl }

    fun repoUnderTestContainer(
        initObjects: Collection<ContStudent> = emptyList(),
        randomUuid: () -> String = { uuid4().toString() },
    ): RepoStudSQL {
        return RepoStudSQL(SqlProperties(url, USER, PASS, SCHEMA, dropDatabase = true),
            initObjects, randomUuid = randomUuid)
    }
}
