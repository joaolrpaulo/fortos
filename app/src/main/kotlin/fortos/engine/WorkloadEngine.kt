package fortos.engine

import fortos.model.WorkloadContext

interface WorkloadEngine {
    fun call(workloadContext: WorkloadContext)
}
