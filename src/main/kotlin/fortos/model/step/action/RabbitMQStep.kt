package fortos.model.step.action

import fortos.engine.processor.EngineProcessor
import fortos.engine.processor.action.RabbitMQEngineProcessor
import fortos.model.step.Step

@EngineProcessor(RabbitMQEngineProcessor::class)
data class RabbitMQStep(
    override val type: String,
    val credentials: RabbitMQCredentials
) : Step(type, null)

data class RabbitMQCredentials(
    val host: String,
    val port: Int = 5672,
    val user: String,
    val password: String
)
