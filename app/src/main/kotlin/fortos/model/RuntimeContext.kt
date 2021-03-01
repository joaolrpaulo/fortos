package fortos.model

import fortos.model.step.Step

data class RuntimeContext(
    val pipeline: List<Step>
)
