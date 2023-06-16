package ru.ak.contingent.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class ContStudentId(private val id: Int) {
    fun asInt() = id

    companion object {
        val NONE = ContStudentId(0)
    }
}
