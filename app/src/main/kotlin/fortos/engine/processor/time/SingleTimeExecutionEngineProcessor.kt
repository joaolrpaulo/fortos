package fortos.engine.processor.time

import java.time.Instant

class SingleTimeExecutionEngineProcessor : BaseTimeEngineProcessor {
    private val startedAt = Instant.now()

    override fun call(input: Any): TimeEngineProcessorData {
        TODO("Not yet implemented")
    }
}
