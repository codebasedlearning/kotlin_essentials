// (C) 2025 A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x01.c_control_flow
import java.lang.RuntimeException

/*======================================================================================================================
Obviously we need structural elements to control the program flow. That is what the snippet is for.
======================================================================================================================*/

fun main() {
    println("Kotlin Essentials -> Control Flow | Exceptions")

    introduceExceptions()
}

/*======================================================================================================================
[Exceptions]

Working with exceptions.
  - 'throw' is an expression.
  - 'try-catch' is an expression. The returned value of a try expression is either the last expression
    in the try block or the last expression in the catch block(s).
  - Kotlin does not have checked exceptions.
  Ref.:
  - https://kotlinlang.org/docs/exceptions.html
======================================================================================================================*/
fun introduceExceptions() {
    println("\n[Exceptions]\n--")

    try {
        // val n = "123".toInt()
        val n = "abc".toInt()                                   // throws
        println(" 1| n=$n")
    } catch (e: NumberFormatException) {
        println(" 2| gotcha: $e")
    } finally {                                                 // optional
        println(" 3| in any case")
    }

    try {
        val n = "abc".toIntOrNull() ?: throw RuntimeException("wrong format")   // 'throw' as expression
        println(" 4| n=$n")
    } catch (e: RuntimeException) {
        println(" 5| gotcha: $e")
    }

    val n = try { "abc".toInt() } catch (e: NumberFormatException) { null }     // 'try' as expression
    println(" 6| n=$n")
}
