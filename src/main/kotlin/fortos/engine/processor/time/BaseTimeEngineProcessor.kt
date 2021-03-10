package fortos.engine.processor.time

import fortos.engine.processor.BaseEngineProcessor
import fortos.utils.Extensions
import kotlin.concurrent.thread

interface BaseTimeEngineProcessor : BaseEngineProcessor<List<TimeEngineProcessorData>> {
    fun execute(timeEngineProcessorData: TimeEngineProcessorData, executors: List<() -> Any>): List<Any> {
        while(timeEngineProcessorData.shouldProceed()) {
            timeEngineProcessorData.execute {
                executors.forEachIndexed { internalIdx, executor ->
                    thread(name = "time-executor-${timeEngineProcessorData.id}-internal-$internalIdx") {
                        executor.invoke()
                    }
                }
            }
    
            timeEngineProcessorData.wait()
        }
        
        return emptyList()
    }
}

data class TimeEngineProcessorData(
    val id: String = Extensions.randomId(),
    val execute: (() -> Any) -> Any,
    val shouldProceed: () -> Boolean,
    val wait: () -> Unit,
)
