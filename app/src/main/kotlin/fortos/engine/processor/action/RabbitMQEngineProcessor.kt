package fortos.engine.processor.action

import fortos.model.step.Step
import org.slf4j.LoggerFactory

class RabbitMQEngineProcessor : BaseActionEngineProcessor {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun call(input: Step): () -> Unit {
        return { logger.info("Sending event") }
    }
}
