package fortos.engine.processor.action

import fortos.gateway.RabbitMQClient
import fortos.gateway.RabbitMQGateway
import fortos.model.step.Step
import fortos.model.step.action.RabbitMQStep
import org.slf4j.LoggerFactory

class RabbitMQEngineProcessor(
    private val rabbitMQGateway: RabbitMQGateway
) : BaseActionEngineProcessor {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun call(input: Step): ActionEngineProcessorData {
        val rabbitStep = input as RabbitMQStep
        val rabbitMQClient = rabbitStep.let {
            RabbitMQClient(
                it.exchange, it.queue, listOf(it.routingKey),
                it.credentials.host, it.credentials.user, it.credentials.password, it.credentials.port
            )
        }

        return ActionEngineProcessorData(
            execute = {
                logger.info("Sending event")

                rabbitMQGateway.publishEvent(rabbitMQClient, input.exchange, input.routingKey, input.event)
            },
            teardown = rabbitMQClient::close
        )
    }
}
