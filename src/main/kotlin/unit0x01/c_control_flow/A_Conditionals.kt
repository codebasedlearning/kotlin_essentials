// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

package unit0x01.c_control_flow

/*======================================================================================================================
Obviously we need structural elements to control the program flow. That is what the snippet is for.
======================================================================================================================*/

fun main() {
    println("Kotlin Essentials -> Control Flow | Conditionals")

    introduceIf()
    introduceWhen()
}

/*======================================================================================================================
[If and When]

Working with 'if' and 'when' (aka switch-case).
  - Classical 'if-else' as it is known from Java.
  - In Kotlin 'if' and 'when' are also an expression, i.e. they can be assigned.
  Ref.:
  - https://kotlinlang.org/docs/control-flow.html
======================================================================================================================*/
fun introduceIf() {
    println("\n[If]\n---")

    val a = 1
    val b = 2
    if ((a > b && (2<3)) || (10>11)) {                          // classical
        println(" 1| max($a,$b)=$a")
    } else {
        println(" 2| max($a,$b)=$b")
    }

    val maxShort = if (a > b) a else b                          // as expression
    println(" 3| max($a,$b)=$maxShort")

    val maxBlock = if (a > b) {                                 // as block expression, last value in block counts
        println(" 4| a>b")
        a
    } else {
        println(" 5| a<=b")
        b
    }
    println(" 6| max($a,$b)=$maxBlock")
}

/*======================================================================================================================
[If and When]

Working with 'if' and 'when' (aka switch-case).
  - 'when' is like 'switch-case' with cases more complex.
  Ref.:
  - https://kotlinlang.org/docs/control-flow.html
======================================================================================================================*/
fun introduceWhen() {
    println("\n[When]\n---")

    val i = 5
    when (i) {                                                  // 'switch'
        in 1..5 -> println(" 1| in range 1..5")
        6 -> println(" 2| is 6")
        else -> println(" 3| <1 or >6")
    }

    val j = when(i) {                                           // as expression
        5 -> 6
        else -> 7
    }
    println(" 4| j=$j")
}
