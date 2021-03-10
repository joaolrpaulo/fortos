package fortos.service

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import fortos.engine.WorkloadEngine
import fortos.helpers.itShould
import fortos.model.BootContext
import fortos.model.RuntimeContext
import fortos.model.constants.DefaultContext
import fortos.service.implementation.BootstrapImpl
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class BootstrapTest: Spek({
    
    val argumentParser = mock<ArgumentParser>()
    val workloadParser = mock<WorkloadParser>()
    val workloadEngine = mock<WorkloadEngine>()

    val subject = BootstrapImpl(argumentParser, workloadParser, workloadEngine)
    
    describe(".call") {
        context("when the file is a valid YML") {
            itShould("call the engine method") {
                val bootContext = BootContext(DefaultContext.FORTOS_PIPELINE_FILE)
                
                whenever(argumentParser.call(emptyArray())).thenReturn(bootContext)
                whenever(workloadParser.call(bootContext.fileName)).thenReturn(RuntimeContext(emptyList()))
                
                subject.call(emptyArray())
        
                verify(workloadEngine).call(emptyList())
            }
        }
    }
})