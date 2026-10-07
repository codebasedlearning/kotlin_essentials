// (C) A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

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
  - '..<' (rangeUntil) is the operator form of 'until', stable since Kotlin 1.9.
  - 'for' over ranges and arrays compiles to a plain counted loop (no iterator object).
  Ref.:
  - https://kotlinlang.org/docs/control-flow.html
======================================================================================================================*/
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

    print(" 3| for 1..<4: ")
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

    print(" 5| l.forEach: ")                                    // also possible (functional style), but
    listOf(1, 2, 3, 4).forEach { i ->                           // break and continue are not directly available
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
  - Nice to know: labeled breaks, and local returns from lambdas.
  - 'return@label' (or the implicit 'return@forEach') returns from the lambda only, i.e. forEach continues
    with the next element - it behaves like 'continue'. A plain 'return' would leave the enclosing function.
  - Non-local 'break' and 'continue' (Kotlin 2.2): inside the lambda of an inline function (like 'run', 'let')
    they refer to the enclosing loop.
  Ref.:
  - https://kotlinlang.org/docs/returns.html
  - https://kotlinlang.org/docs/inline-functions.html#break-and-continue
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
        if (it == 3) return@label                               // returns from the lambda only (like 'continue'),
        println("  | it=$it")                                   // forEach goes on with the next element
    }

    // non-local break and continue (Kotlin 2.2)
    val inputs = listOf("1", "x", "3", "stop", "5")
    var sum = 0
    for (s in inputs) {
        val n = s.toIntOrNull() ?: run {                        // 'run' is inline, so 'break' and 'continue'
            if (s == "stop") break                              // refer to the 'for' loop
            continue
        }
        sum += n
    }
    println(" 2| sum of numbers until 'stop': $sum")
}
