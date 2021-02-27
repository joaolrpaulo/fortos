package fortos.engine

import fortos.model.WorkloadContext
import org.slf4j.LoggerFactory

class WorkloadEngineImpl : WorkloadEngine {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun call(workloadContext: WorkloadContext) {
        logger.info("Engine has started!")
    }
}
