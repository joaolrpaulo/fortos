package fortos.helpers

import org.spekframework.spek2.dsl.Skip
import org.spekframework.spek2.dsl.TestBody
import org.spekframework.spek2.style.specification.Suite

fun Suite.itShould(description: String, skip: Skip = Skip.No, timeout: Long = delegate.defaultTimeout, body: TestBody.() -> Unit) {
    it("it should $description", skip, timeout, body)
}
