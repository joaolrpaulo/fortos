package fortos.model.step.timer

import fortos.engine.processor.EngineProcessor
import fortos.engine.processor.time.SingleTimeExecutionEngineProcessor
import fortos.model.step.Step

@EngineProcessor(SingleTimeExecutionEngineProcessor::class)
data class SingleTimerStep(
    override val type: String,
    override val workload: List<Step>,
): Step(type, workload)

