package fortos.model.step.timer

import fortos.engine.processor.EngineProcessor
import fortos.engine.processor.time.ConstantTimerEngineProcessor
import fortos.model.step.Step

@EngineProcessor(ConstantTimerEngineProcessor::class)
data class ConstantTimerStep(
    override val type: String,
    override val workload: List<Step>,
    override val executionMetadata: ConstantTimerExecutionMetadata
) : Step(type, workload, null, executionMetadata)

data class ConstantTimerExecutionMetadata(
    val transactions: Long,
    val duration: Long,
    val threads: Long,
)
