package fortos.engine.processor.time

import fortos.model.step.Step
import fortos.model.step.timer.ConstantTimerStep
import java.time.Instant
import java.util.concurrent.atomic.AtomicInteger

class ConstantTimerEngineProcessor : BaseTimeEngineProcessor {
    private val startedAt = Instant.now()
    private val transactionsPerformed = AtomicInteger()

    override fun call(input: Step): List<TimeEngineProcessorData> {
        val timerDefinition = input as ConstantTimerStep
        val endTime = startedAt.plusSeconds(timerDefinition.duration)
        val waitTime: Long =
            if (timerDefinition.transactions == 1L) 0L
            else timerDefinition.duration * 1000 / timerDefinition.transactions

        return List(timerDefinition.threads.toInt()) {
            TimeEngineProcessorData(
                execute = { if (transactionsPerformed.incrementAndGet() <= timerDefinition.transactions) it() },
                shouldProceed = { Instant.now().isBefore(endTime) },
                wait = { Thread.sleep(waitTime) }
            )
        }
    }
}
