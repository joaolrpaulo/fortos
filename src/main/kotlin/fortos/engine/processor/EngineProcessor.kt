package fortos.engine.processor

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class EngineProcessor(
    val value: KClass<out BaseEngineProcessor<*>>
)
