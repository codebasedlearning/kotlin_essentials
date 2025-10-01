// (C) 2025 A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x02.exercises.proposed_solutions

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private fun main() {
    println("\nProposed solution 'Magmas'\n--")

    println("1 | start processing")
    runBlocking {
        processNumbers().take(5).collect { println("a | value $it")}
    }
}

private fun processNumbers()
    = flow {
        for (i in 1..100) {
            delay(500)
            emit(i)
        }
    }
    .filter { it % 2 == 0 }
    .map { it * it }
