package fortos.model.processor

data class ConstantTimerProcessor(
    override val type: String,
    override val workload: List<Processor>,
    val transactions: Long,
    val duration: Long,
    val threads: Long,
): Processor(type, workload)

