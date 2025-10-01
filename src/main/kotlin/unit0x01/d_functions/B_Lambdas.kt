// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

package unit0x01.d_functions

/*======================================================================================================================
Functions are everywhere - especially in this snippet. We show how they are declared and used.
======================================================================================================================*/

fun main() {
    println("Kotlin Essentials -> Functions | Lambdas")

    introduceLambdas()
}

/*======================================================================================================================
[Lambdas / High-order functions]

Working with lambdas and trailing lambdas.
  - Use lambdas if you have a local in-place functionality.
    When thinking of assigning a lambda to a (named) variable, consider a local function instead.
  - So-called 'trailing lambda' means providing the last argument as a block of code after the call.
  - Type example: sum: (Int, Int) -> Int
 Ref.:
  - https://kotlinlang.org/docs/lambdas.html
======================================================================================================================*/
fun introduceLambdas() {
    println("\n[Lambdas / High-order functions]\n---")

    fun applyOp(x: Int, y: Int, op: (Int, Int) -> Int) = op(x, y)   // local function (high-order function)
    // val sum: (Int, Int) -> Int = { x, y -> x + y }               // 'sum' references a lambda expression (avoid it)
    // val sum23 = applyOp(2, 3, { x, y -> x + y})                  // calling with in-place lambda
    val sum23 = applyOp(2, 3) { x, y -> x + y }                     // and now with trailing lambda
    println(" 1| 2+3=$sum23")

    fun eval(x: Int, block: (Int) -> Int) = block(x)
    val inc2 = eval(23, { x->x+2 })                             // classical
    println(" 2| inc2=23+2=$inc2")
    val inc3 = eval(42) { x->x+3 }                              // lambda out of parentheses
    println(" 3| inc3=42+3=$inc3")
    val inc4 = eval(95) { it+4 }                                // parameter not named, so it is 'it'
    println(" 4| inc4=95+4=$inc4")
}
