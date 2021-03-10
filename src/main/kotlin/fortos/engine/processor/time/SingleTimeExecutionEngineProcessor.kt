package fortos.engine.processor.time

import fortos.model.step.Step
import java.util.concurrent.atomic.AtomicInteger

class SingleTimeExecutionEngineProcessor : BaseTimeEngineProcessor {
    private val transactionsPerformed = AtomicInteger()
    
    override fun call(input: Step): List<TimeEngineProcessorData> {
        return listOf(
            TimeEngineProcessorData(
                execute = { if (transactionsPerformed.incrementAndGet() <= 1L) it() },
                shouldProceed = { transactionsPerformed.get() <= 1L },
                wait = { }
            )
        )
    }
}
