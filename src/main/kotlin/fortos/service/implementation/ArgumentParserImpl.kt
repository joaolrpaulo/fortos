package fortos.service.implementation

import fortos.model.BootContext
import fortos.model.constants.DefaultContext
import fortos.service.ArgumentParser
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default

class ArgumentParserImpl : ArgumentParser {
    override fun call(cliArguments: Array<String>): BootContext {
        val parser = ArgParser("bootstrap")
        val fileName by parser.option(
            ArgType.String,
            shortName = "f",
            description = "File containing the definition for the WTBD"
        ).default(DefaultContext.FORTOS_PIPELINE_FILE)

        parser.parse(cliArguments)

        return BootContext(fileName)
    }
}
