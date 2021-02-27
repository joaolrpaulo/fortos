package fortos.service

interface Bootstrap<in I, out O> {
    fun call(cliArguments: I): O
}
