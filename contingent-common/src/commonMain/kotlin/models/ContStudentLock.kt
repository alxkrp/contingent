package ru.ak.contingent.common.models

@JvmInline
value class ContStudentLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ContStudentLock("")
    }
}
