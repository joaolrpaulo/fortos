package fortos.engine.processor.action

import org.slf4j.LoggerFactory

class LogActionProcessor : BaseActionEngineProcessor {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun call(input: Any): () -> Unit {
        return { logger.info("Just logging my presence here!") }
    }
}
