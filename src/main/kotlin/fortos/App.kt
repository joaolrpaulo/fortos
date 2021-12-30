package fortos

import fortos.module.mainModule
import fortos.service.Bootstrap
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.logger.SLF4JLogger

class Main : KoinComponent {
    private val bootstrap by inject<Bootstrap<Array<String>, Unit>>()

    fun start(cliArguments: Array<String>) = bootstrap.call(cliArguments)
}

fun main(cliArguments: Array<String>) {

    startKoin {
        // use Koin logger
        SLF4JLogger()
        // declare modules
        modules(mainModule)
    }

    Main().start(cliArguments)
}
