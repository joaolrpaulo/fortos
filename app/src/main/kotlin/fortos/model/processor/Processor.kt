package fortos.model.processor

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import fortos.model.constants.WorkloadConstants
import fortos.model.processor.action.RabbitMQProcessor
import fortos.model.processor.timebased.ConstantTimerProcessor

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes(value = [
    JsonSubTypes.Type(value = RabbitMQProcessor::class, name = WorkloadConstants.RABBITMQ_PROCESSOR),
    JsonSubTypes.Type(value = ConstantTimerProcessor::class, name = WorkloadConstants.CONSTANT_TIME_PROCESSOR),
])
open class Processor(
    open val type: String,
    open val workload: List<Processor>?
)
