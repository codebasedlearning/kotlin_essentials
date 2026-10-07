// (C) A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x02.exercises

//import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

private fun main() {
    println("\nSolution 'Ribbles'\n--")

    println("1 | start download")
    measureTimeMillis {
        // ...
        // downloadFiles(downloadTimes = listOf(1000L, 500L, 1300L))
        // ...
    }.let { println("2 | overall download time: $it") }
}

// [...] fun downloadFiles(downloadTimes: List<Long>) [...]
