package ru.ak.contingent.common.models

@JvmInline
value class ContStudentId(private val id: Int) {
    fun asInt() = id

    companion object {
        val NONE = ContStudentId(0)
    }
}
