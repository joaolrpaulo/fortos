package fortos.engine.processor.time

import fortos.engine.processor.BaseEngineProcessor

interface BaseTimeEngineProcessor : BaseEngineProcessor<TimeEngineProcessorData>

data class TimeEngineProcessorData(
    val shouldProceed: () -> Boolean,
    val wait: () -> Long,
)
