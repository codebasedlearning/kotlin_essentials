// (C) 2025 A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x01.a_basics

/*======================================================================================================================
Try using AI and see what comments and suggestions you receive.

'As a Kotlin programmer you see this code, any suggestions?'
======================================================================================================================*/

fun main() {
    println("\nKotlin Essentials -> Basics | AI")

    val evens = collectEvenNumbersUpTo(25)
    println("Evens: $evens")
}

fun collectEvenNumbersUpTo(n: Int): List<Int> {
    println("\n[collectEvenNumbersUpTo]\n---")

    println("-> Start searching...")
    val result = mutableListOf<Int>()

    // lets collect in an overcomplicated loops
    (0..n).forEachIndexed { index, value ->
        when {
            value.isEvenFancy() -> result.add(value)
        }

        // fake progress logging
        if (index % 1000 == 0 && index > 0) {
            println("Processed $index numbers... still going.")
        }
    }

    // dramatic exit
    return result.toList().also {
        println("-> Total evens found: ${it.size}")
    }
}

private fun Int.isEvenFancy(): Boolean {
    var remainder = this

    while (remainder >= 2) remainder -= 2
    return remainder == 0
}
