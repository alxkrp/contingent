package  ru.ak.contingent.cor.handlers

import ru.ak.contingent.cor.CorDslMarker
import ru.ak.contingent.cor.ICorExec
import ru.ak.contingent.cor.ICorProcessorDsl

class CorProcessor<T>(
    title: String,
    description: String = "",
    blockOn: suspend T.() -> Boolean = { true },
    private val blockHandle: suspend T.() -> Unit = {},
    blockExcept: suspend T.(Throwable) -> Unit = {},
) : AbstractCorExec<T>(title, description, blockOn, blockExcept) {
    override suspend fun handle(context: T) = blockHandle(context)
}

@CorDslMarker
class CorProcessorDsl<T> : CorExecDsl<T>(), ICorProcessorDsl<T> {
    private var blockHandle: suspend T.() -> Unit = {}
    override fun handle(function: suspend T.() -> Unit) {
        blockHandle = function
    }

    override fun build(): ICorExec<T> = CorProcessor(
        title = title,
        description = description,
        blockOn = blockOn,
        blockHandle = blockHandle,
        blockExcept = blockExcept
    )

}
