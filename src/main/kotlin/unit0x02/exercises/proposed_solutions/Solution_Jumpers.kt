// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

package unit0x02.exercises.proposed_solutions

private fun main() {
    println("\nProposed solution 'Jumpers'\n--")

    println("1 | fibs ${fibonacci().take(10).toList()}")
}

private fun fibonacci() = sequence {
    var pair = Pair(0, 1)
    while (true) {
        yield(pair.first)
        pair = Pair(pair.second, pair.first + pair.second)
    }
}
