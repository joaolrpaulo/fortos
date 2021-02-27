package fortos.model

import fortos.model.processor.Processor

data class WorkloadContext(
    val workload: List<Processor>
)
