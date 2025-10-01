// (C) 2025 A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x02.b_concurrency

/*======================================================================================================================
This snippet introduces coroutines and suspending functions with async.
======================================================================================================================*/

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {
    println("Kotlin Essentials -> Concurrency | Async")

    introduceAsync()
}

/*======================================================================================================================
[Async]

Give it back to me.
  - Conceptually, 'async' is just like 'launch'. It starts a separate coroutine which is a light-weight thread
    that works concurrently with all the other coroutines.
  - Optionally, 'async' can be made lazy by setting its start parameter.
  - 'Deferred' (the type) represents a concept known by other names such as 'Future' or 'Promise'.
    It stores a computation, but it defers the moment you get the final result; it promises the result sometime
    in the future. But beware, depending on the language and context, the specifics and implementations of
    'Promise' and 'Future' can vary greatly.
  - For a stream of values consider 'Flows' (or, obsolet from Kotlin 1.5, 'Channels').
  Ref.:
  - https://kotlinlang.org/docs/composing-suspending-functions.html#concurrent-using-async
  - https://kotlinlang.org/docs/coroutines-and-channels.html#concurrency
======================================================================================================================*/
fun introduceAsync() = runBlocking {
    println("\n[Async]\n---")

    suspend fun calcFirstResult(): Int {
        delay(1000L)                                            // pretend we are doing something useful here
        println(" a| . calcFirstResult done")
        return 13
    }

    suspend fun calcSecondResult(): Int {
        delay(1000L)                                            // pretend we are doing something useful here, too
        println(" b| . calcSecondResult done")
        return 29
    }

    var sum = 0

    println(" 1| start calculations (synchronous)")
    measureTimeMillis {
        val n1 = calcFirstResult()
        val n2 = calcSecondResult()
        sum = n1 + n2
    }.let { println(" 2| done after ${it}ms, sum=$sum\n") }

    // The structure of this asynchronous code is very similar to synchronous code,
    // which is one of the main advantages of this approach.
    // Compare it with other strategies such as callbacks when something is done
    // (callback hell).
    println(" 3| start calculations (asynchronous)")
    measureTimeMillis {
        val n1 = async { calcFirstResult() }                    // type Deferred<Int>
        val n2 = async { calcSecondResult() }
        sum = n1.await() + n2.await()
    }.let { println(" 4| done after ${it}ms, sum=$sum\n") }

    println(" 5| start a list of calculations (asynchronous)")
    measureTimeMillis {
        val futures = listOf(
            async { calcFirstResult() },
            async { calcSecondResult() }
        )
        sum = futures.awaitAll().sum()
    }.let { println(" 6| done after ${it}ms, sum=$sum\n") }
}
