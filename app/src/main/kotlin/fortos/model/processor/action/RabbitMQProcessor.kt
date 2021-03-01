package fortos.model.processor.action

import fortos.engine.processor.EngineProcessor
import fortos.engine.processor.action.RabbitMQEngineProcessor
import fortos.model.processor.Processor

@EngineProcessor(RabbitMQEngineProcessor::class)
data class RabbitMQProcessor(
    override val type: String,
    val credentials: RabbitMQCredentials
): Processor(type, null)

data class RabbitMQCredentials(
    val host: String,
    val port: Int = 5672,
    val user: String,
    val password: String
)
