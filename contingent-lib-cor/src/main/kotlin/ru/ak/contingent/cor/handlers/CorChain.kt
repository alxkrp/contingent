package ru.ak.contingent.cor.handlers

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.ak.contingent.cor.CorDslMarker
import ru.ak.contingent.cor.ICorChainDsl
import ru.ak.contingent.cor.ICorExec
import ru.ak.contingent.cor.ICorExecDsl

/**
 * Реализация цепочки (chain), которая исполняет свои вложенные цепочки и процессоры
 * в соответствии со стратегией handler
 */
class CorChain<T>(
    private val execs: List<ICorExec<T>>,
    private val handler: suspend (T, List<ICorExec<T>>) -> Unit,
    title: String,
    description: String = "",
    blockOn: suspend T.() -> Boolean = { true },
    blockExcept: suspend T.(Throwable) -> Unit = {},
) : AbstractCorExec<T>(title, description, blockOn, blockExcept) {
    override suspend fun handle(context: T) = handler(context, execs)
}

/**
 * Стратегия последовательного исполнения
 */
suspend fun <T> executeSequential(context: T, execs: List<ICorExec<T>>): Unit =
    execs.forEach {
        it.exec(context)
    }

/**
 * Стратегия параллельного исполнения
 */
suspend fun <T> executeParallel(context: T, execs: List<ICorExec<T>>): Unit = coroutineScope {
    execs.forEach {
        launch { it.exec(context) }
    }
}

@CorDslMarker
class CorChainDsl<T>(
    private val handler: suspend (T, List<ICorExec<T>>) -> Unit = ::executeSequential,
) : CorExecDsl<T>(), ICorChainDsl<T> {
    private val processor: MutableList<ICorExecDsl<T>> = mutableListOf()
    override fun add(processor: ICorExecDsl<T>) {
        this.processor.add(processor)
    }

    override fun build(): ICorExec<T> = CorChain(
        title = title,
        description = description,
        execs = processor.map { it.build() },
        handler = handler,
        blockOn = blockOn,
        blockExcept = blockExcept
    )
}
