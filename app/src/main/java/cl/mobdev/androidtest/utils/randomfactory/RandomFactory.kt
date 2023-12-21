package cl.mobdev.androidtest.utils.randomfactory

import kotlin.random.Random

object RandomFactory {
    fun generateRandomString(): String {
        val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..9)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }
}
