package fortos.engine

import fortos.engine.processor.BaseEngineProcessor
import fortos.engine.processor.EngineProcessor
import fortos.engine.processor.action.BaseActionEngineProcessor
import fortos.engine.processor.time.BaseTimeEngineProcessor
import fortos.model.step.Step
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.isSubclassOf

class WorkloadEngineImpl(
    private val actionProcessors: List<BaseActionEngineProcessor>,
    private val timeProcessors: List<BaseTimeEngineProcessor>,
) : WorkloadEngine {
    private val logger = LoggerFactory.getLogger(this::class.java)

    private val tearDownHandlers = mutableListOf<()-> Unit>()
    private val submittedThreads = AtomicInteger()

    override fun call(workloadContext: List<Step>) {
        val workload = orchestrate(workloadContext)

        workload.forEach { it.invoke() }

        tearDownHandlers.forEach { it.invoke() }
    }

    private fun prepareTimeProcessor(engineProcessor: Step): () -> Unit {
        val timeProcessor = loadTimeProcessor(engineProcessor::class.findAnnotation<EngineProcessor>()!!.value)
        val timerDefinitions = timeProcessor.call(engineProcessor)
        val executors = orchestrate(engineProcessor.workload ?: emptyList())

        if (logger.isDebugEnabled) {
            val executorsRegistered = timerDefinitions.map { it.id }

            logger.debug("Registered executors for type ${timeProcessor::class.simpleName} are: $executorsRegistered")
        }

        submittedThreads.addAndGet(timerDefinitions.fold(0) { acc, current -> acc + current.submittedJobsCount})

        return {
            timerDefinitions.forEachIndexed { idx, timer ->
                thread(name = "fortos-engine-$idx") { timeProcessor.execute(timer, executors) }
            }
        }
    }

    private fun prepareActionProcessor(engineProcessor: Step) =
        loadActionProcessor(engineProcessor::class.findAnnotation<EngineProcessor>()!!.value).call(engineProcessor)

    private fun orchestrate(workloadContext: List<Step>) =
        workloadContext.map { processor ->
            when {
                instanceOf(processor, BaseTimeEngineProcessor::class) -> prepareTimeProcessor(processor)
                instanceOf(processor, BaseActionEngineProcessor::class) -> {
                    prepareActionProcessor(processor).let { tearDownHandlers.add(it.teardown); it.execute }
                }
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
