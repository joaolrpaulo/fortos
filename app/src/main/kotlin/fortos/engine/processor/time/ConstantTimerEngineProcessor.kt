package fortos.engine.processor.time

import fortos.model.step.Step
import fortos.model.step.timer.ConstantTimerStep
import java.time.Instant
import java.util.concurrent.atomic.AtomicInteger

class ConstantTimerEngineProcessor : BaseTimeEngineProcessor {
    private val transactionsPerformed = AtomicInteger()

    override fun call(input: Step): List<TimeEngineProcessorData> {
        val timerDefinition = input as ConstantTimerStep
        val endTime = Instant.now().plusSeconds(timerDefinition.duration)
        val maxTransactions = timerDefinition.transactions * timerDefinition.duration
        val waitTime = (1000 / timerDefinition.transactions.toFloat()) * timerDefinition.threads.toFloat()

        return List(timerDefinition.threads.toInt()) {
            TimeEngineProcessorData(
                execute = { if (transactionsPerformed.incrementAndGet() <= maxTransactions) it() },
                shouldProceed = { Instant.now().isBefore(endTime) && transactionsPerformed.get() <= maxTransactions.toInt() },
                wait = { Thread.sleep(waitTime.toLong()) }
            )
        }
    }
}
