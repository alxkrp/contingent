package ru.ak.contingent.blackbox.fixture.db

import mu.KotlinLogging
import java.sql.DriverManager

private val log = KotlinLogging.logger {}

class PostgresClearer : DbClearer {
    private var initialized = false
    private val connection by lazy {
        initialized = true
        DriverManager.getConnection("jdbc:postgresql://localhost/contingent", "user", "password")
    }

    override fun clear() {
        connection.createStatement().use { statement ->
            val rows = statement.executeUpdate("delete from student")
            log.info("    delete $rows row(s) from student")
        }
    }

    override fun close() {
        if (initialized) connection.close()
    }
}