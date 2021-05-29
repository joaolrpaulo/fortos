package fortos.gateway

import com.fasterxml.jackson.databind.JsonNode
import com.rabbitmq.client.BuiltinExchangeType
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.MessageProperties
import org.slf4j.LoggerFactory
import java.util.UUID
import java.util.concurrent.CountDownLatch

class RabbitMQClient(
    exchange: String,
    queue: String,
    routingKeys: List<String>,
    private val host: String,
    private val user: String,
    private val password: String,
    private val port: Int,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val connectionFactory by lazy {
        ConnectionFactory().also {
            it.username = user
            it.password = password
            it.host = host
            it.port = port
        }
    }
    private val channelFactory by lazy { ChannelFactory() }
    private val countDownLatch = CountDownLatch(0)

    init { QueueFactory.initializeQueue(channelFactory.acquireChannel(), exchange, queue, routingKeys) }

    fun publish(exchange: String, routingKey: String, payload: ByteArray) {
        countDownLatch.
        channelFactory.acquireChannel().use {
            it.basicPublish(exchange, routingKey, messageProperties(), payload)
        }.also { it.count }
    }

    fun close() = channelFactory.close()

    private fun messageProperties() = MessageProperties.PERSISTENT_BASIC.builder()
        .contentType("application/json")
        .messageId(UUID.randomUUID().toString())
        .build()

    private inner class ChannelFactory {
        private var connection = createConnection()

        fun acquireChannel(): Channel = getOrCreateConnection().createChannel().also { it.confirmSelect() }

        fun close() {
            logger.info("Closing all open connections!")

            if (connection.isOpen) {
                connection.close()
            }
        }

        private fun getOrCreateConnection(): Connection {
            if (!connection.isOpen) {
                connection = createConnection()
            }

            return connection
        }

        private fun createConnection() = connectionFactory.newConnection()
    }

    private object QueueFactory {
        fun initializeQueue(channel: Channel, exchange: String, queue: String, routingKeys: List<String>) {
            channel.use {
                channel.exchangeDeclare(exchange, BuiltinExchangeType.TOPIC, true)

                channel.queueDeclare(queue, true, false, false, emptyMap())

                routingKeys.forEach { key ->
                    channel.queueBind(queue, exchange, key)
                }
            }
        }
    }
}
