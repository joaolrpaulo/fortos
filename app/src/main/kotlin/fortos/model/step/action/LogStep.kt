package fortos.model.step.action;

import fortos.engine.processor.EngineProcessor;
import fortos.engine.processor.action.LogActionProcessor;
import fortos.model.step.Step

@EngineProcessor(LogActionProcessor::class)
data class LogStep(
    override val type: String
): Step(type, null)
