package fortos.engine.processor.action

import fortos.engine.processor.BaseEngineLambdas
import fortos.model.step.action.LogExecutionMetadata
import org.slf4j.LoggerFactory

class LogActionProcessor(
    private val setupMetadata: Any?
) : BaseActionEngineProcessor {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun call(input: Any?): BaseEngineLambdas {
        val loggerStep = input as LogExecutionMetadata

        return BaseEngineLambdas(
            { logger.info(loggerStep.message) },
            { }
        )
    }
}
