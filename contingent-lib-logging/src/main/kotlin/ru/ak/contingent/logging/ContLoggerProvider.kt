package ru.ak.contingent.logging

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

class ContLoggerProvider(
    private val provider: (String) -> IContLogWrapper = { IContLogWrapper.DEFAULT }
) {
    fun logger(loggerId: String) = provider(loggerId)
    fun logger(clazz: KClass<*>) = provider(clazz.qualifiedName ?: clazz.simpleName ?: "(unknown)")
    fun logger(function: KFunction<*>) = provider(function.name)
}
