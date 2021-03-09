package fortos.utils

object Extensions {
    private val randomCharPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    
    fun randomId() = (1..10)
        .map { kotlin.random.Random.nextInt(0, randomCharPool.size) }
        .map(randomCharPool::get)
        .joinToString("")
}

