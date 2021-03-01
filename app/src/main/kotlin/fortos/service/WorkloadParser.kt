package fortos.service

import fortos.model.RuntimeContext

interface WorkloadParser {
    fun call(fileName: String): RuntimeContext
}
