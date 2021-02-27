package fortos.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

data class WorkloadContext(
    val workload: Workload
)

data class Workload(
    val processors: List<Processor>
)

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes(value = [
    JsonSubTypes.Type(value = RabbitMQProcessor::class, name = WorkloadConstants.RABBITMQ_PROCESSOR),
])
open class Processor(
    open val type: String
)

