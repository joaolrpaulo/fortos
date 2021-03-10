package fortos.service

import fortos.model.BootContext

interface ArgumentParser {
    fun call(cliArguments: Array<String>): BootContext
}
