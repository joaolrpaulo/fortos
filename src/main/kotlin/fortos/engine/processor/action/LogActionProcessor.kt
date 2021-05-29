package fortos.engine.processor.action

import fortos.model.step.Step
import org.slf4j.LoggerFactory
import java.util.concurrent.CountDownLatch

class LogActionProcessor : BaseActionEngineProcessor {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun call(input: Step): ActionEngineProcessorData {
        return ActionEngineProcessorData(execute = { logger.info("Just logging my presence here!") })
    }
}
