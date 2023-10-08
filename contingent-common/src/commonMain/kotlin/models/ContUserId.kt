package ru.ak.contingent.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class ContUserId(private val id: String) {
    fun asString() = id
    companion object {
        val NONE = ContUserId("")
    }
}
