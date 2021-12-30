package fortos.engine.processor.action

import com.rabbitmq.client.Channel
import fortos.configurations.RabbitMQConfiguration
import fortos.engine.processor.BaseEngineLambdas
import fortos.model.step.Step
import fortos.model.step.action.RabbitMQCredentials
import fortos.model.step.action.RabbitMQExecutionMetadata
import fortos.model.step.action.RabbitMQStep
import org.slf4j.LoggerFactory

class RabbitMQEngineProcessor(
    setupMetadata: RabbitMQCredentials
) : BaseActionEngineProcessor {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private val rabbitMQPublisher: Channel = RabbitMQConfiguration.publisher(setupMetadata)

    override fun call(input: Any?): BaseEngineLambdas {
        val rabbitMQStep = input as RabbitMQExecutionMetadata

        return BaseEngineLambdas(
            {
                rabbitMQPublisher.basicPublish("", rabbitMQStep.queueName, null, "Some Message".toByteArray())
                logger.info("Sending event")
            },
            {
                if (rabbitMQPublisher.connection.isOpen) rabbitMQPublisher.connection.close()
            }
        )
    }
}
