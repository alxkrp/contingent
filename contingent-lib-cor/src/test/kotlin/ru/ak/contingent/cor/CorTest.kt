package ru.ak.contingent.cor

import kotlinx.coroutines.test.runTest
import ru.ak.contingent.cor.handlers.CorChain
import ru.ak.contingent.cor.handlers.CorProcessor
import ru.ak.contingent.cor.handlers.executeSequential
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class CorTest {

    @Test
    fun processorShouldHandle() = runTest {
        val processor = CorProcessor<TestContext>(
            title = "p1",
            blockHandle = { history += "p1," }
        )
        val ctx = TestContext()
        processor.exec(ctx)
        assertEquals("p1,", ctx.history)
    }

    @Test
    fun processorShouldNotExecute() = runTest {
        val processor = CorProcessor<TestContext>(
            title = "p1",
            blockOn = { status == CorStatuses.ERROR },
            blockHandle = { history += "p1," }
        )
        val ctx = TestContext()
        processor.exec(ctx)
        assertEquals("", ctx.history)
    }

    @Test
    fun processoShouldHandleException() = runTest {
        val processor = CorProcessor<TestContext>(
            title = "w1",
            blockHandle = { throw RuntimeException("some error") },
            blockExcept = { e -> history += e.message }
        )
        val ctx = TestContext()
        processor.exec(ctx)
        assertEquals("some error", ctx.history)
    }

    @Test
    fun chainShouldExecuteProcessors() = runTest {
        val createProcessor = {
            title: String ->
            CorProcessor<TestContext>(
                title = title,
                blockOn = { status == CorStatuses.NONE },
                blockHandle = { history += "$title," }
            )
        }
        val chain = CorChain<TestContext>(
            execs = listOf(createProcessor("p1"), createProcessor("p2")),
            title = "chain",
            handler = ::executeSequential
        )
        val ctx = TestContext()
        chain.exec(ctx)
        assertEquals("p1,p2,", ctx.history)
    }

    private suspend fun execute(dsl: ICorExecDsl<TestContext>): TestContext {
        val ctx = TestContext()
        dsl.build().exec(ctx)
        return ctx
    }

    @Test
    fun handleShouldExecute() = runTest {
        assertEquals("p1,", execute(rootChain {
            processor {
                handle { history += "p1," }
            }
        }).history)
    }

    @Test
    fun onShouldCheckCondition() = runTest {
        assertEquals("p2,p3,", execute(rootChain {
            processor {
                on { status == CorStatuses.ERROR }
                handle { history += "p1," }
            }
            processor {
                on { status == CorStatuses.NONE }
                handle {
                    history += "p2,"
                    status = CorStatuses.FAILING
                }
            }
            processor {
                on { status == CorStatuses.FAILING }
                handle { history += "p3," }
            }
        }).history)
    }

    @Test
    fun exceptShouldExecuteWhenException() = runTest {
        assertEquals("some error", execute(rootChain {
            processor {
                handle { throw RuntimeException("some error") }
                except { history += it.message }
            }
        }).history)
    }

    @Test
    fun shouldThrowWhenExceptionAndNoExcept() = runTest {
        assertFails {
            execute(rootChain {
                processor("throw") { throw RuntimeException("some error") }
            })
        }
    }

    @Test
    fun someComplexChain() = runTest {
        val chain = rootChain<TestContext> {
            processor {
                title = "Инициализация статуса"
                description = "При старте обработки цепочки, статус еще не установлен. Проверяем его"

                on { status == CorStatuses.NONE }
                handle { status = CorStatuses.RUNNING }
                except { status = CorStatuses.ERROR }
            }

            chain {
                on { status == CorStatuses.RUNNING }

                processor(
                    title = "Лямбда обработчик",
                    description = "Пример использования обработчика в виде лямбды"
                ) {
                    some += 4
                }
            }

            parallel {
                on {
                    some < 15
                }

                processor(title = "Increment some") {
                    some++
                }
            }

            printResult()

        }.build()

        val ctx = TestContext()
        chain.exec(ctx)
        println("Complete: $ctx")
    }
}

private fun ICorChainDsl<TestContext>.printResult() = processor(title = "Print example") {
    println("some = $some")
}

data class TestContext(
    var status: CorStatuses = CorStatuses.NONE,
    var some: Int = 0,
    var history: String = "",
)

enum class CorStatuses {
    NONE,
    RUNNING,
    FAILING,
    ERROR
}
