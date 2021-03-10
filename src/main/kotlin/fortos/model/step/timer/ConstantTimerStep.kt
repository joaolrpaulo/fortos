package fortos.model.step.timer

import fortos.engine.processor.EngineProcessor
import fortos.engine.processor.time.ConstantTimerEngineProcessor
import fortos.model.step.Step

@EngineProcessor(ConstantTimerEngineProcessor::class)
data class ConstantTimerStep(
    override val type: String,
    override val workload: List<Step>,
    val transactions: Long,
    val duration: Long,
    val threads: Long,
) : Step(type, workload)
