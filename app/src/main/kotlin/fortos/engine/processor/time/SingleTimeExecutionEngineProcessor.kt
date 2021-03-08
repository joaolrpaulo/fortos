package fortos.engine.processor.time

import fortos.model.step.Step
import java.time.Instant

class SingleTimeExecutionEngineProcessor : BaseTimeEngineProcessor {
    private val startedAt = Instant.now()

    override fun call(input: Step): TimeEngineProcessorData {
        TODO("Not yet implemented")
    }
}
