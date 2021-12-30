package fortos.engine.processor

interface BaseEngineProcessor<out O> {
    fun call(input: Any?): O
}

data class BaseEngineLambdas(
    val runtime: () -> Unit,
    val teardown: () -> Unit,
)