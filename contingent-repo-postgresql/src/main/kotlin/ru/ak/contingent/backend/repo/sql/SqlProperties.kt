package ru.ak.contingent.backend.repo.sql

open class SqlProperties(
    val url: String = "jdbc:postgresql://localhost:5432/contingent",
    val user: String = "postgres",
    val password: String = "contingent-pass",
    val schema: String = "contingent",
    // Delete tables at startup - needed for testing
    val dropDatabase: Boolean = false,
)
