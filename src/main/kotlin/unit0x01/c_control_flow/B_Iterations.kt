// (C) 2025 A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x01.c_control_flow

/*======================================================================================================================
Obviously we need structural elements to control the program flow. That is what the snippet is for.
======================================================================================================================*/

fun main() {
    println("Kotlin Essentials -> Control Flow | Iterations")

    introduceFor()

    println("\n-- More --")
    introduceDoWhile()
    introduceLabels()
}

/*======================================================================================================================
[Iterations]

Working with 'for'.
  - 'for', 'do' and 'while' work as it is known from Java.
  - Same is true for 'break' and 'continue'.
  - @OptIn(ExperimentalStdlibApi::class) enables the new operator '..<' (instead of 'until').
  Ref.:
  - https://kotlinlang.org/docs/control-flow.html
======================================================================================================================*/
// @OptIn(ExperimentalStdlibApi::class) // for Kotlin < 1.9
fun introduceFor() {
    println("\n[Iterations]\n---")

    // classical for
    print(" 1| for 1..4: ")
    for (i in 1..4) {
        print("i=$i ")
    }
    println()

    print(" 2| for 1 until 4: ")
    for (i in 1 until 4) {                                      // ..<
        print("i=$i ")
    }
    println()

    print(" 3| for 1..<3:")
    for (i in 1 ..< 4) {                                        // ..<
        print("i=$i ")
    }
    println()

    // for-each
    val l = listOf(1, 2, 3, 4)
    print(" 4| for i in l: ")
    for (i in l) {
        print("i=$i ")
    }
    println()

    print(" 5| for i in l: ")                                   // also possible, sometimes faster (why?)
    listOf(1, 2, 3, 4).forEach { i ->                           // but break and continue difficult
        print("i=$i ")
    }
    println()
}

/*======================================================================================================================
[Iterations]

Working with 'do' and 'while'.
  - 'for', 'do' and 'while' work as it is known from Java.
  - Same is true for 'break' and 'continue'.
  Ref.:
  - https://kotlinlang.org/docs/control-flow.html
======================================================================================================================*/
fun introduceDoWhile() {
    println("\n[Do/While]\n---")

    print(" 1| while <4: ")
    var i = 1
    while (i<4) {
        print("i=$i ")
        ++i
    }
    println()

    print(" 2| do while <4: ")
    i = 1
    do {
        print("i=$i ")
        ++i
    } while (i<4)
    println()
}

/*======================================================================================================================
[Iterations]

Working with labels.
  - Nice to know: if-blocks and labeled breaks.
  Ref.:
  - https://kotlinlang.org/docs/control-flow.html
======================================================================================================================*/
fun introduceLabels() {
    println("\n[More on Control-Flow]\n---")

    // break with label, stop outer loop
    var steps = 0
    loop@ for (j1 in 1..5) {                                    // 1.1 ... 1.5 (5 steps)
        for (j2 in 1..5) {                                      // 2.1, 2.2 break => 7 steps
            ++steps
            if (j1 == 2 && j2 == 2)
                break@loop
        }
    }
    println(" 1| steps:$steps")

    val numbers = listOf(1, 2, 3, 4, 5)
    numbers.forEach label@ {
        if (it == 3) return@label                               // Returns to the caller of forEach
        println("  | it=$it")
    }
}
