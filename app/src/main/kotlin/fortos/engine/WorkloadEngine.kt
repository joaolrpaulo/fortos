package fortos.engine

import fortos.model.processor.Processor

interface WorkloadEngine {
    fun call(workloadContext: List<Processor>)
}
