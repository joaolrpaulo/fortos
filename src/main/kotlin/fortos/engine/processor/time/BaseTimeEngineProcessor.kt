package fortos.engine.processor.time

import fortos.engine.processor.BaseEngineLambdas
import fortos.engine.processor.BaseEngineProcessor
import fortos.utils.Extensions
import kotlin.concurrent.thread

interface BaseTimeEngineProcessor : BaseEngineProcessor<List<TimeEngineProcessorData>> {
    fun execute(timeEngineProcessorData: TimeEngineProcessorData, executors: List<BaseEngineLambdas>): List<Any> {
        while (timeEngineProcessorData.shouldProceed()) {
            timeEngineProcessorData.execute {
                executors.forEachIndexed { internalIdx, executor ->
                    thread(name = "time-executor-${timeEngineProcessorData.id}-internal-$internalIdx") {
                        executor.runtime.invoke()
                    }
                }
            }

            timeEngineProcessorData.wait()
        }

        executors.forEach { it.teardown.invoke() }

        return emptyList()
    }
}

data class TimeEngineProcessorData(
    val id: String = Extensions.randomId(),
    val execute: (() -> Any) -> Any,
    val shouldProceed: () -> Boolean,
    val wait: () -> Unit,
)
