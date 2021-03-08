package fortos.engine.processor.time

import fortos.engine.processor.BaseEngineProcessor

interface BaseTimeEngineProcessor : BaseEngineProcessor<List<TimeEngineProcessorData>>

data class TimeEngineProcessorData(
    val execute: (() -> Any) -> Any,
    val shouldProceed: () -> Boolean,
    val wait: () -> Unit,
)
