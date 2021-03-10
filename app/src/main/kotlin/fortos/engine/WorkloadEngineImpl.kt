package fortos.engine

import fortos.engine.processor.BaseEngineProcessor
import fortos.engine.processor.EngineProcessor
import fortos.engine.processor.action.BaseActionEngineProcessor
import fortos.engine.processor.time.BaseTimeEngineProcessor
import fortos.model.step.Step
import org.slf4j.LoggerFactory
import kotlin.concurrent.thread
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

        orchestrate(workloadContext).forEach { it.invoke() }
    }

    private fun prepareTimeProcessor(engineProcessor: Step) : () -> Unit {
        val timeProcessor = loadTimeProcessor(engineProcessor::class.findAnnotation<EngineProcessor>()!!.value)
        val timerDefinitions = timeProcessor.call(engineProcessor)
        val executors = orchestrate(engineProcessor.workload ?: emptyList())

        return {
            timerDefinitions.forEachIndexed { idx, timer ->
                thread(name = "fortos-engine-$idx") {
                    timeProcessor.execute(timer, executors)
                }
            }
        }
    }

    private fun prepareActionProcessor(engineProcessor: Step) =
        loadActionProcessor(engineProcessor::class.findAnnotation<EngineProcessor>()!!.value).call(engineProcessor)

    private fun orchestrate(workloadContext: List<Step>) =
        workloadContext.map { processor ->
            when {
                instanceOf(processor, BaseTimeEngineProcessor::class) -> prepareTimeProcessor(processor)
                instanceOf(processor, BaseActionEngineProcessor::class) -> prepareActionProcessor(processor)
                else -> error("Input cannot be processed since only Action / Timer Processors are allowed")
            }
        }

    private fun instanceOf(processor: Step, engineProcessor: KClass<out BaseEngineProcessor<*>>) =
        processor::class.findAnnotation<EngineProcessor>()?.value?.isSubclassOf(engineProcessor) ?: false

    private fun loadActionProcessor(engineProcessor: KClass<out BaseEngineProcessor<*>>) =
        actionProcessors.first { processor -> engineProcessor.isSubclassOf(processor::class) }

    private fun loadTimeProcessor(engineProcessor: KClass<out BaseEngineProcessor<*>>) =
        timeProcessors.first { processor -> engineProcessor.isSubclassOf(processor::class) }
}
