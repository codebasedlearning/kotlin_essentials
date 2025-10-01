// (C) 2025 A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x02.exercises.proposed_solutions

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

private fun main() {
    println("\nProposed solution 'Ribbles'\n--")

    println("1 | start download")
    measureTimeMillis {
        runBlocking {
            downloadFiles(downloadTimes = listOf(1000L, 500L, 1300L))
        }
    }.let { println("2 | overall download time: $it") }
}

private suspend fun downloadFiles(downloadTimes: List<Long>) {
    withContext(Dispatchers.IO) {
        for ((index, time) in downloadTimes.withIndex()) {
            launch {
                println("a | download file no ${index + 1} started")
                delay(time)
                println("b | download file no ${index + 1} completed after ${time}ms")
            }
        }
    }
}
