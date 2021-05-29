package fortos.engine.processor.action

import fortos.engine.processor.BaseEngineProcessor

interface BaseActionEngineProcessor : BaseEngineProcessor<ActionEngineProcessorData>

data class ActionEngineProcessorData(
    val execute: () -> Unit,
    val teardown: () -> Unit = { }
)
