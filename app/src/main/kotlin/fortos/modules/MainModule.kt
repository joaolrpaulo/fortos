package fortos.modules

import com.fasterxml.jackson.databind.ObjectMapper
import fortos.configurations.ObjectMapperConfiguration
import fortos.engine.WorkloadEngine
import fortos.engine.WorkloadEngineImpl
import fortos.service.ArgumentParser
import fortos.service.Bootstrap
import fortos.service.WorkloadParser
import fortos.service.implementation.ArgumentParserImpl
import fortos.service.implementation.BootstrapImpl
import fortos.service.implementation.WorkloadParserImpl
import org.koin.dsl.module

val mainModule = module {
    single { ObjectMapperConfiguration().loadObjectMapper() }
    single<ArgumentParser> { ArgumentParserImpl() }
    single<WorkloadParser> { WorkloadParserImpl(get()) }
    single<WorkloadEngine> { WorkloadEngineImpl() }
    single<Bootstrap<Array<String>, Unit>> { BootstrapImpl(get(), get(), get()) }
}
