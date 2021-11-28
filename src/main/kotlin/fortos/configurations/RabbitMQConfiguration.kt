package fortos.configurations

import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory
import fortos.model.step.action.RabbitMQCredentials

class RabbitMQConfiguration {

    companion object {
        fun publisher(credentials: RabbitMQCredentials): Channel {
            val factory = ConnectionFactory().apply {
                username = credentials.user
                password = credentials.password
                host = credentials.host
                port = credentials.port
            }

            return factory.newConnection().createChannel()
        }
    }
}