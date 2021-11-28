package fortos.engine.processor.action

import com.rabbitmq.client.Channel
import fortos.configurations.RabbitMQConfiguration
import fortos.model.step.Step
import fortos.model.step.action.RabbitMQStep
import org.slf4j.LoggerFactory

class RabbitMQEngineProcessor : BaseActionEngineProcessor {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private var rabbitMQPublisher: Channel? = null;

    override fun call(input: Step): () -> Unit {
        val rabbitMQStep = input as RabbitMQStep

        rabbitMQPublisher = rabbitMQPublisher ?: RabbitMQConfiguration.publisher(rabbitMQStep.credentials)

        return {
            rabbitMQPublisher?.basicPublish("", rabbitMQStep.queueName, null, "Some Message".toByteArray())
            logger.info("Sending event")
        }
    }
}
