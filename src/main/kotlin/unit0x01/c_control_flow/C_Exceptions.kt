// (C) A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x01.c_control_flow

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
  - Idiomatic helpers: 'require(cond)' (IllegalArgumentException), 'check(cond)' (IllegalStateException),
    'error(msg)', and 'runCatching { }' which wraps the outcome in a 'Result'.
  Ref.:
  - https://kotlinlang.org/docs/exceptions.html

Result:
  - e.g. val result = runCatching { "123".toInt() }
  - runCatching executes the lambda and captures its outcome:
    - Success: Returns Result containing the computed value
    - Failure: Returns Result containing the thrown exception.
  - members like isSuccess, isFailure, getOrNull(), getOrDefault(),
    getOrElse(), getOrThrow(), exceptionOrNull()
======================================================================================================================*/
fun introduceExceptions() {
    println("\n[Exceptions]\n---")

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

    val result = runCatching { "abc".toInt() }                  // Result<Int>, success or failure
    println(" 7| value=${result.getOrElse { -1 }}, failure=${result.exceptionOrNull()?.javaClass?.simpleName}")
}
