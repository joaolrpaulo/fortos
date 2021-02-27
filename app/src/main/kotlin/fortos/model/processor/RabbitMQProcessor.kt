package fortos.model.processor

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
