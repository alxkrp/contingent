package ru.ak.contingent.common.models

data class ContError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val level: Level = Level.ERROR,
    val exception: Throwable? = null,
) {
    enum class Level {
        ERROR, WARN, INFO
    }
}
