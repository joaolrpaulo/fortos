package fortos.service

import fortos.model.BootContext
import fortos.model.constants.DefaultContext
import fortos.service.implementation.ArgumentParserImpl
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ArgumentParserTest {
    val subject = ArgumentParserImpl()

    @DisplayName("when all the required arguments are given")
    @Nested
    inner class WhenAllArgumentsAreGiven {

        @Test
        fun `it should respect them and return the proper BootContext Object`() {
            val arguments = arrayOf("-f", "fileName.txt")

            assertEquals(
                expected = BootContext(fileName = "fileName.txt"),
                actual = subject.call(arguments)
            )
        }
    }

    @DisplayName("when there is a missing argument")
    @Nested
    inner class WhenOneArgumentIsNotGiven {
        @Test
        fun `and this argument is the fileName, it should respect them and return the proper BootContext Object`() {
            assertEquals(
                expected = BootContext(fileName = DefaultContext.FORTOS_PIPELINE_FILE),
                actual = subject.call(emptyArray())
            )
        }
    }
}
