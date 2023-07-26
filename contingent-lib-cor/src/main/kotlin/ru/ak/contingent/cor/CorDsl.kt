package ru.ak.contingent.cor

import ru.ak.contingent.cor.handlers.CorChainDsl
import ru.ak.contingent.cor.handlers.CorProcessorDsl
import ru.ak.contingent.cor.handlers.executeParallel

/**
 * Базовый билдер (dsl)
 */
@CorDslMarker
interface ICorExecDsl<T> {
    var title: String
    var description: String

    fun on(function: suspend T.() -> Boolean)
    fun except(function: suspend T.(e: Throwable) -> Unit)
    fun build(): ICorExec<T>
}

/**
 * Билдер (dsl) для цепочек (chain)
 */
@CorDslMarker
interface ICorChainDsl<T> : ICorExecDsl<T> {
    fun add(processor: ICorExecDsl<T>)
}

/**
 * Билдер (dsl) для процессоров (processor)
 */
@CorDslMarker
interface ICorProcessorDsl<T> : ICorExecDsl<T> {
    fun handle(function: suspend T.() -> Unit)
}

/**
 * Точка входа в dsl построения цепочек.
 * Элементы исполняются последовательно.
 *
 * Пример:
 * ```
 *  chain<SomeContext> {
 *      processor {
 *      }
 *      chain {
 *          processor(...) {
 *          }
 *          processor(...) {
 *          }
 *      }
 *      parallel {
 *         ...
 *      }
 *  }
 * ```
 */
fun <T> rootChain(function: ICorChainDsl<T>.() -> Unit): ICorChainDsl<T> = CorChainDsl<T>().apply(function)


/**
 * Создает цепочку, элементы которой исполняются последовательно.
 */
fun <T> ICorChainDsl<T>.chain(function: ICorChainDsl<T>.() -> Unit) {
    add(CorChainDsl<T>().apply(function))
}

/**
 * Создает цепочку, элементы которой исполняются параллельно.
 * Будьте аккуратны с доступом к контексту - при необходимости используйте синхронизацию
 */
fun <T> ICorChainDsl<T>.parallel(function: ICorChainDsl<T>.() -> Unit) {
    add(CorChainDsl<T>(::executeParallel).apply(function))
}

/**
 * Создает процессор
 */
fun <T> ICorChainDsl<T>.processor(function: ICorProcessorDsl<T>.() -> Unit) {
    add(CorProcessorDsl<T>().apply(function))
}

/**
 * Создает процессор с on и except по умолчанию
 */
fun <T> ICorChainDsl<T>.processor(
    title: String,
    description: String = "",
    blockHandle: T.() -> Unit
) {
    add(CorProcessorDsl<T>().also {
        it.title = title
        it.description = description
        it.handle(blockHandle)
    })
}