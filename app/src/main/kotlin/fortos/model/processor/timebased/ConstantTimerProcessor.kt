package fortos.model.processor.timebased

import fortos.engine.processor.EngineProcessor
import fortos.engine.processor.time.ConstantTimerEngineProcessor
import fortos.model.processor.Processor

@EngineProcessor(ConstantTimerEngineProcessor::class)
data class ConstantTimerProcessor(
    override val type: String,
    override val workload: List<Processor>,
    val transactions: Long,
    val duration: Long,
    val threads: Long,
): Processor(type, workload)

