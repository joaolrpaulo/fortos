package fortos.engine.processor.action

import org.slf4j.LoggerFactory

class RabbitMQEngineProcessor : BaseActionEngineProcessor {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun call(input: Any): () -> Unit {
        return { logger.info("Sending event") }
    }
}
