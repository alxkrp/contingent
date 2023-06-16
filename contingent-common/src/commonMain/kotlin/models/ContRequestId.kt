package ru.ak.contingent.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class ContRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ContRequestId("")
    }
}
