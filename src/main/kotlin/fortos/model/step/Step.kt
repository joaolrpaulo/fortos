package fortos.model.step

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import fortos.model.constants.WorkloadConstants
import fortos.model.step.action.LogStep
import fortos.model.step.action.RabbitMQStep
import fortos.model.step.timer.ConstantTimerStep
import fortos.model.step.timer.SingleTimerStep

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes(value = [
    JsonSubTypes.Type(value = RabbitMQStep::class, name = WorkloadConstants.RABBITMQ_PROCESSOR),
    JsonSubTypes.Type(value = LogStep::class, name = WorkloadConstants.LOG_PROCESSOR),
    JsonSubTypes.Type(value = SingleTimerStep::class, name = WorkloadConstants.SINGLE_TIME_PROCESSOR),
    JsonSubTypes.Type(value = ConstantTimerStep::class, name = WorkloadConstants.CONSTANT_TIME_PROCESSOR),
])
open class Step(
    open val type: String,
    open val workload: List<Step>?
)
