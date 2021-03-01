package fortos.service.implementation

import fortos.engine.WorkloadEngine
import fortos.service.ArgumentParser
import fortos.service.Bootstrap
import fortos.service.WorkloadParser

class BootstrapImpl(
    private val argumentParser: ArgumentParser,
    private val workloadParser: WorkloadParser,
    private val workloadEngine: WorkloadEngine
): Bootstrap<Array<String>, Unit> {
    override fun call(cliArguments: Array<String>) {
        val context = argumentParser.call(cliArguments)
        val workloadContext = workloadParser.call(context.fileName)

        workloadEngine.call(workloadContext.workload)
    }
}
