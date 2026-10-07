// (C) A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x02.exercises.proposed_solutions

private fun main() {
    println("\nProposed solution 'Jumpers'\n--")

    println("1 | fibs ${fibonacci().take(10).toList()}")
}

private fun fibonacci() = sequence {                           // Long, as Int overflows from fib(47) on
    var pair = Pair(0L, 1L)
    while (true) {
        yield(pair.first)
        pair = Pair(pair.second, pair.first + pair.second)
    }
}
