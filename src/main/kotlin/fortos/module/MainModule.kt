package fortos.module

import fortos.configurations.ObjectMapperConfiguration
import fortos.engine.WorkloadEngine
import fortos.engine.WorkloadEngineImpl
import fortos.engine.processor.action.LogActionProcessor
import fortos.engine.processor.action.RabbitMQEngineProcessor
import fortos.engine.processor.time.ConstantTimerEngineProcessor
import fortos.engine.processor.time.SingleTimeExecutionEngineProcessor
import fortos.service.ArgumentParser
import fortos.service.Bootstrap
import fortos.service.WorkloadParser
import fortos.service.implementation.ArgumentParserImpl
import fortos.service.implementation.BootstrapImpl
import fortos.service.implementation.WorkloadParserImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainModule = module {
    single { ObjectMapperConfiguration().loadObjectMapper() }
    single<ArgumentParser> { ArgumentParserImpl() }
    single<WorkloadParser> { WorkloadParserImpl(get()) }
    single<WorkloadEngine> { WorkloadEngineImpl() }
    single<Bootstrap<Array<String>, Unit>> { BootstrapImpl(get(), get(), get()) }
}
