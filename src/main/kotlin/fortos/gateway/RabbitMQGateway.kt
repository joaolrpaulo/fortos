package fortos.gateway

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

class RabbitMQGateway(private val objectMapper: ObjectMapper) {

    fun publishEvent(rabbitMQClient: RabbitMQClient, exchange: String, routingKey: String, event: JsonNode) {
        val payload = objectMapper.writeValueAsBytes(event)

        rabbitMQClient.publish(exchange, routingKey, payload)
    }
}
