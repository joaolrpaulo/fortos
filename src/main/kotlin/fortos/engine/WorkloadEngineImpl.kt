package fortos.engine

import fortos.engine.processor.BaseEngineLambdas
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
import kotlin.reflect.full.primaryConstructor

class WorkloadEngineImpl : WorkloadEngine {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun call(stepList: List<Step>) {
        orchestrate(stepList).forEach { it.runtime.invoke() }
    }

    private fun prepareTimeProcessor(step: Step): BaseEngineLambdas {
        val timeProcessor = loadTimeProcessor(step)
        val timerDefinitions = timeProcessor.call(step.executionMetadata)
        val executors = orchestrate(step.workload ?: emptyList())

        if (logger.isDebugEnabled) {
            val executorsRegistered = timerDefinitions.map { it.id }

            logger.debug("Registered executors for type ${timeProcessor::class.simpleName} are: $executorsRegistered")
        }

        return BaseEngineLambdas(
            {
                timerDefinitions.forEachIndexed { idx, timer ->
                    thread(name = "fortos-engine-$idx") {
                        timeProcessor.execute(timer, executors)
                    }
                }
            },
            { }
        )
    }

    private fun prepareActionProcessor(step: Step) : BaseActionEngineProcessor {
        val engineProcessorKClass = step::class.findAnnotation<EngineProcessor>()!!.value

        return engineProcessorKClass.primaryConstructor!!.call(step.setupMetadata) as BaseActionEngineProcessor
    }

    private fun loadTimeProcessor(step: Step) : BaseTimeEngineProcessor {
        val engineProcessorKClass = step::class.findAnnotation<EngineProcessor>()!!.value

        return engineProcessorKClass.primaryConstructor!!.call() as BaseTimeEngineProcessor
    }

    private fun orchestrate(stepList: List<Step>) =
        stepList.map { step ->
            when {
                instanceOf(step, BaseTimeEngineProcessor::class) -> prepareTimeProcessor(step)
                instanceOf(step, BaseActionEngineProcessor::class) -> prepareActionProcessor(step).call(step.executionMetadata)
                else -> error("Input cannot be processed since only Action / Timer Processors are allowed")
            }
        }

    private fun instanceOf(processor: Step, engineProcessor: KClass<out BaseEngineProcessor<*>>) =
        processor::class.findAnnotation<EngineProcessor>()?.value?.isSubclassOf(engineProcessor) ?: false
}
