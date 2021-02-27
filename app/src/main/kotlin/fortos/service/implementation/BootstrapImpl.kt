package fortos.service.implementation

import fortos.service.ArgumentParser
import fortos.service.Bootstrap
import fortos.service.WorkloadParser
import org.slf4j.LoggerFactory

class BootstrapImpl(
    private val argumentParser: ArgumentParser,
    private val workloadParser: WorkloadParser,
): Bootstrap<Array<String>, Unit> {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun call(cliArguments: Array<String>) {
        val context = argumentParser.call(cliArguments)
        val workload = workloadParser.call(context.fileName)

        logger.info("$context")
    }
}
