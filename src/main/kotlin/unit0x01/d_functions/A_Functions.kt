// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

package unit0x01.d_functions

/*======================================================================================================================
Functions are everywhere - especially in this snippet. We show how they are declared and used.
======================================================================================================================*/

fun main() {
    println("Kotlin Essentials -> Functions | Functions")

    introduceFunctions()
}

/*======================================================================================================================
[Functions]

Defining and calling functions.
  - Functions can be defined with block body ({}) or expression body (=).
  - They can be called with named parameters and defined with default parameters.
  - Name the arguments if there is any chance of confusion as to which argument means what
    e.g. a call with many bool values like f(true, false, true).
  - Functions without return type are of type 'Unit' (something like 'void').
  - Functions are 'first class citizens', i.e. they are objects like other objects. This means they can be assigned,
    used as arguments, etc. And what is more, they can be called.
  - '::' creates a member reference or a class reference, e.g. 'twice' below.
 Ref.:
  - https://kotlinlang.org/docs/functions.html
======================================================================================================================*/

fun isDevilLongVersion(n: Int): Boolean {                       // classical function with param. and return type
    return (n == 6 || n == 66 || n == 666)
}

fun isDevil(n: Int) = (n == 6 || n == 66 || n == 666)           // as expression body, return-type is inferred

fun twice(x: Int): Int = 2 * x                                  // however, return-type can be given

fun addAll(a: Int = 1, b: Int, c: Int = 2) = a + b + c          // with defaults, cf. call

fun nothingInReturn() {}                                        // no return type means 'Unit'

fun introduceFunctions() {
    println("\n[Functions]\n---")

    println(" 1| devil(6):${isDevilLongVersion(6)}")
    println(" 2| devil(5):${isDevil(5)}")
    println(" 3| 5*2=${twice(5)}")
    println(" 4| ?+5+1=${addAll(c = 1, b = 5)}")                // named parameter with a default
    println(" 5| nothing=${nothingInReturn()}")                 // unit can be an expression

    val f: (Int) -> Int = ::twice                               // type is function from Int->Int, e.g. 'twice'
    val g: ((Int) -> Int)? = ::twice                            // like before but nullable
    println(" 6| 5*2=${f(5)}=${g!!(5)}")
}
