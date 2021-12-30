package fortos.model.step.action

import fortos.engine.processor.EngineProcessor
import fortos.engine.processor.action.RabbitMQEngineProcessor
import fortos.model.step.Step

@EngineProcessor(RabbitMQEngineProcessor::class)
data class RabbitMQStep(
    override val type: String,
    override val setupMetadata: RabbitMQCredentials = RabbitMQCredentials(),
    override val executionMetadata: RabbitMQExecutionMetadata = RabbitMQExecutionMetadata(),
) : Step(type, null, setupMetadata, executionMetadata)

data class RabbitMQCredentials(
    val host: String = "localhost",
    val port: Int = 5672,
    val user: String = "guest",
    val password: String = "guest"
)

data class RabbitMQExecutionMetadata(
    val queueName: String = "test"
)
