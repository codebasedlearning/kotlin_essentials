// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

package unit0x01.exercises.proposed_solutions

private fun main() {
    println("\nProposed solution 'Halos'\n--")
    solution()
}

private fun solution() {
    do {
        print("Enter number: ")
// classical...
//        var line: String
//        try {
//            line = readln()
//        } catch(e: RuntimeException) {
//            line = ""
//        }
//        var n: Int
//        try {
//            n = line.toInt() ?: -1
//        } catch(e: NumberFormatException) {
//            n = -1
//        }

// example: with try-catch as expression
//        val n = try { readlnOrNull()?.toInt() ?: -1 } catch(e: NumberFormatException) { -1 }

        // avoiding exceptions and null
        val line = readlnOrNull()
        val n = line?.toIntOrNull() ?: -1

        println("Your text was '$line' and it converts to $n.")
    } while (n>=0)
}
