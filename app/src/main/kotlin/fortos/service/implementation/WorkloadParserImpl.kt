package fortos.service.implementation

import com.fasterxml.jackson.databind.ObjectMapper
import fortos.model.WorkloadContext
import fortos.service.WorkloadParser
import org.slf4j.LoggerFactory
import java.io.File

class WorkloadParserImpl(
    private val objectMapper: ObjectMapper
) : WorkloadParser {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun call(fileName: String) {
        val file = File(fileName)
        logger.info("Processing workload for -> $fileName")

        val workloadContext = objectMapper.readValue(file, WorkloadContext::class.java)
        logger.info("Processed context $workloadContext")
    }
}
