package fortos

import fortos.modules.mainModule
import fortos.service.Bootstrap
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.logger.SLF4JLogger

@KoinApiExtension
class Main : KoinComponent {
    private val bootstrap by inject<Bootstrap<Array<String>, Unit>>()

    fun start(cliArguments: Array<String>) = bootstrap.call(cliArguments)
}

@KoinApiExtension
fun main(cliArguments: Array<String>) {

    startKoin {
        // use Koin logger
        SLF4JLogger()
        // declare modules
        modules(mainModule)
    }

    Main().start(cliArguments)
}
