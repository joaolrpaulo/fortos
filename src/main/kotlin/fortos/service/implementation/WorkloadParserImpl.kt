package fortos.service.implementation

import com.fasterxml.jackson.databind.ObjectMapper
import fortos.model.RuntimeContext
import fortos.service.WorkloadParser
import org.slf4j.LoggerFactory
import java.io.File

class WorkloadParserImpl(private val objectMapper: ObjectMapper) : WorkloadParser {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun call(fileName: String) : RuntimeContext {
        logger.debug("Processing workload for -> $fileName")

        return objectMapper.readValue(File(fileName), RuntimeContext::class.java)
    }
}
