package fortos.model

data class RabbitMQProcessor(
    override val type: String,
    val credentials: RabbitMQCredentials
): Processor(type)

data class RabbitMQCredentials(
    val host: String,
    val port: Int,
    val user: String,
    val password: String
)
