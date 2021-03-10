package fortos.service

import fortos.helpers.itShould
import fortos.model.BootContext
import fortos.model.constants.DefaultContext
import fortos.service.implementation.ArgumentParserImpl
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

class ArgumentParserTest : Spek({
    val subject = ArgumentParserImpl()

    describe(".call") {
        context("when all the required arguments are given") {
            itShould("respect them and return the proper BootContext Object") {
                val arguments = arrayOf("-f", "fileName.txt")

                assertEquals(
                    expected = BootContext(fileName = "fileName.txt"),
                    actual = subject.call(arguments)
                )
            }
        }

        context("when there is a missing argument") {
            context("and this argument is the fileName") {
                itShould("use the default one") {
                    assertEquals(
                        expected = BootContext(fileName = DefaultContext.FORTOS_PIPELINE_FILE),
                        actual = subject.call(emptyArray())
                    )
                }
            }
        }
    }
})
