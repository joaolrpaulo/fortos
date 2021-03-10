package fortos.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import fortos.helpers.itShould
import fortos.model.RuntimeContext
import fortos.model.constants.DefaultContext
import fortos.service.implementation.WorkloadParserImpl
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.File

class WorkloadParserTest : Spek({
    val objectMapper = mock<ObjectMapper>()

    val subject = WorkloadParserImpl(objectMapper)

    describe(".call") {
        whenever(objectMapper.readValue(any<File>(), eq(RuntimeContext::class.java)))
            .thenReturn(RuntimeContext(emptyList()))

        itShould("delegate the responsibility to Jackson") {
            subject.call(DefaultContext.FORTOS_PIPELINE_FILE)

            verify(objectMapper).readValue(any<File>(), eq(RuntimeContext::class.java))
        }
    }
})
