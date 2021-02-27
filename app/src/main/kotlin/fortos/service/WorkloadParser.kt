package fortos.service

import fortos.model.WorkloadContext

interface WorkloadParser {
    fun call(fileName: String): WorkloadContext
}
