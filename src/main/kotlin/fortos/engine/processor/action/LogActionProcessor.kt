package fortos.engine.processor.action

import fortos.model.step.Step
import fortos.model.step.action.LogStep
import org.slf4j.LoggerFactory

class LogActionProcessor : BaseActionEngineProcessor {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun call(input: Step): () -> Unit {
        val loggerStep = input as LogStep
        return { logger.info(loggerStep.message) }
    }
}
