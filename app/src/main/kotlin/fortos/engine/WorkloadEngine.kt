package fortos.engine

import fortos.model.step.Step

interface WorkloadEngine {
    fun call(workloadContext: List<Step>)
}
