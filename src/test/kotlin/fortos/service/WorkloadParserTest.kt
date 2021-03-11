package fortos.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import fortos.model.RuntimeContext
import fortos.model.constants.DefaultContext
import fortos.service.implementation.WorkloadParserImpl
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.File

class WorkloadParserTest {
    val objectMapper = mock<ObjectMapper>()

    val subject = WorkloadParserImpl(objectMapper)

    @Nested
    @DisplayName("when called")
    inner class WhenCalled {
        @Test
        fun `delegate the responsibility to Jackson`() {
            whenever(objectMapper.readValue(any<File>(), eq(RuntimeContext::class.java)))
                .thenReturn(RuntimeContext(emptyList()))

            subject.call(DefaultContext.FORTOS_PIPELINE_FILE)

            verify(objectMapper).readValue(any<File>(), eq(RuntimeContext::class.java))
        }
    }
}
