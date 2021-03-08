package fortos.engine.processor

import fortos.model.step.Step

interface BaseEngineProcessor<out O> {
    fun call(input: Step): O
}
