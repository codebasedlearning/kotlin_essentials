// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

package unit0x02.b_concurrency

import kotlinx.coroutines.CoroutineScope
import unit0x02.utils.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread
import kotlin.random.Random
import kotlin.time.TimeSource

/*======================================================================================================================
This snippet shows how to start and join a classical thread.
======================================================================================================================*/

fun main() {
    println("Kotlin Essentials -> Concurrency | Threads")

    introduceThreads()
}

/*======================================================================================================================
[Threads]

Classic approach.
  - Threads work in a similar way in many languages. Here we restrict ourselves to start and join,
    as we focus more on coroutines and the tools within.
  Ref.:
  - https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.concurrent/thread.html
======================================================================================================================*/
fun introduceThreads() {
    println("\n[Threads]\n---")

    val mark = ConcurrencyInfo()

    println("${mark("1")} start thread 'Alan'")

    val alan = thread {                                               // this also starts the thread
        println("${mark("a")} started (as ${threadName()}), observe WiFi RSSI for 400ms")   // Thread.currentThread().name
        repeat(4) {
            val rssi = readWiFiRSSI()
            println("${mark("b")} - RSSI: ${rssi}dB")
        }
        println("${mark("c")} observation done, end of thread")
    }
    println("${mark("2")} observe temperatures for 200ms")

    repeat(2) {
        val temp = readTemperatur()
        println("${mark("3")} - temperature: ${temp}°C")
    }

    println("${mark("4")} observation done, create 'Beth'")

    val beth = thread(start = false) { // do not start directly, you can also name it
        println("${mark("d")} started (as ${threadName()}), observe WiFi Latency for 300ms")
        repeat(3) {
            val latency = readWiFiLatency()
            println("${mark("e")} - Latency: ${latency}ms")
        }
        println("${mark("f")} observation done, end of thread")
    }

    println("${mark("5")} start 'Beth'")
    beth.start()

    println("${mark("6")} wait for 'Alan'")
    alan.join()

    println("${mark("7")} woke up, 'Alan' joined, wait for 'Beth'")
    beth.join()

    println("${mark("8")} woke up, 'Beth' joined, end of main")
}
