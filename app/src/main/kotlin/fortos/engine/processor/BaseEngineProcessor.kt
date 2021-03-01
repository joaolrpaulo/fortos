package fortos.engine.processor

interface BaseEngineProcessor<out O> {
    fun call(input: Any): O
}
