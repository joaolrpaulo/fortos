package fortos.service

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import fortos.engine.WorkloadEngine
import fortos.model.BootContext
import fortos.model.RuntimeContext
import fortos.model.constants.DefaultContext
import fortos.service.implementation.BootstrapImpl
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class BootstrapTest {

    val argumentParser = mock<ArgumentParser>()
    val workloadParser = mock<WorkloadParser>()
    val workloadEngine = mock<WorkloadEngine>()

    val subject = BootstrapImpl(argumentParser, workloadParser, workloadEngine)

    @DisplayName("When the file is valid")
    @Nested
    inner class FileIsValid {

        @Test
        fun `it should call boot the engine`() {
            val bootContext = BootContext(DefaultContext.FORTOS_PIPELINE_FILE)

            whenever(argumentParser.call(emptyArray())).thenReturn(bootContext)
            whenever(workloadParser.call(bootContext.fileName)).thenReturn(RuntimeContext(emptyList()))

            subject.call(emptyArray())

            verify(workloadEngine).call(emptyList())
        }
    }
}
