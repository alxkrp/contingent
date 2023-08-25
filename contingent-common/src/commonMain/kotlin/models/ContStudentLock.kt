package ru.ak.contingent.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class ContStudentLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ContStudentLock("")
    }
}
