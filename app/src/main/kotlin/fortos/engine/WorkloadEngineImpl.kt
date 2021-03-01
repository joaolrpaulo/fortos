package fortos.engine

import fortos.engine.processor.BaseEngineProcessor
import fortos.engine.processor.EngineProcessor
import fortos.engine.processor.action.BaseActionEngineProcessor
import fortos.engine.processor.time.BaseTimeEngineProcessor
import fortos.model.step.Step
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.isSubclassOf

class WorkloadEngineImpl(
    private val actionProcessors: List<BaseActionEngineProcessor>,
    private val timeProcessors: List<BaseTimeEngineProcessor>,
)  : WorkloadEngine {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun call(workloadContext: List<Step>) {
        logger.info("Engine has started!")

        workloadContext.forEach { workloadProcessor ->
            when {
                instanceOf(workloadProcessor, BaseTimeEngineProcessor::class) -> prepareTimeProcessor(workloadProcessor)
                instanceOf(workloadProcessor, BaseActionEngineProcessor::class) -> prepareActionProcessor(workloadProcessor).invoke()
            }
        }
    }

    private fun prepareTimeProcessor(workloadProcessor: Step) {
        TODO("Not yet implemented.")
    }

    private fun prepareActionProcessor(workloadProcessor: Step) : () -> Unit {
        val engineProcessor = workloadProcessor::class.findAnnotation<EngineProcessor>()!!.value

        return loadActionProcessor(engineProcessor).call(workloadProcessor)
    }

    private fun instanceOf(processor: Step, engineProcessor: KClass<out BaseEngineProcessor<*>>) =
        processor::class.findAnnotation<EngineProcessor>()?.value?.isSubclassOf(engineProcessor) ?: false

    private fun loadActionProcessor(engineProcessor: KClass<out BaseEngineProcessor<*>>) =
        actionProcessors.first { processor -> engineProcessor.isSubclassOf(processor::class) }

    private fun loadTimeProcessor(engineProcessor: KClass<out BaseEngineProcessor<*>>) =
        timeProcessors.first { processor -> engineProcessor.isSubclassOf(processor::class) }
}
