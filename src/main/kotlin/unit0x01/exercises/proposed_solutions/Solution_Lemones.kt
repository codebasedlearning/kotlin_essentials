// (C) 2025 A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x01.exercises.proposed_solutions

private fun main() {
    println("\nProposed solution 'Lemones'\n---")
    solution()
}

// Part I: 205
const val data_part1 = """
    3POeQx4
    h5g6srvOA70N
    f8BMx7BgE8y
    rLHp3jcN
"""

// Part II: 349
const val data_part2 = """
    sixwF1Uone
    9xGHzn3one
    8eightwoUP
    oneightwo2
    3sevenine7
    Hho6sixQYqWR 
"""

private fun solution() {
    println("Sum Example 1: ${solve(data_part1.trimIndent().lines(), true)}")
    println("Sum Example 2: ${solve(data_part2.trimIndent().lines(), false)}")
}

fun solve(lines: List<String>, part1: Boolean): Int {
    val wordDigits = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    val wordValues = wordDigits.mapIndexed { index, word -> word to (index + 1).toString() }.toMap()

    fun chkDigit(c: Char): Char? {
        return if (c.isDigit()) c else null
    }

    fun chkWord(line: String, pos: Int): String? {
        for ((word, value) in wordValues) {
            if (line.startsWith(word, pos)) {
                return value
            }
        }
        return null
    }

    var result = 0
    for (line in lines) {
        val digits = mutableListOf<String>()
        val ranges = listOf(0 until line.length, (line.length - 1 downTo 0))

        for (rg in ranges) {
            for (pos in rg) {
                val digit = chkDigit(line[pos])
                if (digit != null) {
                    digits.add(digit.toString())
                    break
                } else if (!part1) {
                    val wordDigit = chkWord(line, pos)
                    if (wordDigit != null) {
                        digits.add(wordDigit)
                        break
                    }
                }
            }
        }
        // println("-- $digits")
        result += (digits.first() + digits.last()).toInt()
    }

    return result
}
