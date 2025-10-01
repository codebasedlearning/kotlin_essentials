// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

package unit0x01.b_variables

/*======================================================================================================================
This snippet is about primitive data types.
======================================================================================================================*/

fun main() {
    println("\nKotlin Essentials -> Variables | Primitives")

    introducePrimitives()

    println("\n-- More --")
    moreOnPrimitives()
}

/*======================================================================================================================
[Primitives]

Working with basic types.
  - "In Kotlin, everything is an object in the sense that you can call member functions and properties on
    any variable. Some types can have a special internal representation – for example, numbers, characters
    and booleans can be represented as primitive values at runtime – but to the user they look like
    ordinary class instances."
  More:
  - There are more primitive types: Float, Short, Byte.
  - You can ask for the type of the variable, but note that writing code that depends on a concrete type
    ('if' cascades) has a code smell to it (even if there is a lot of code that does this).
  Ref.:
  - https://kotlinlang.org/docs/basic-types.html
======================================================================================================================*/
fun introducePrimitives() {
    println("\n[Primitives]\n---")

    val i = 23                                                  // type Int (32 bit)
    // val i: Int = 23                                          // same with type
    val l = 21L                                                 // type Long, inferred
    val c = 'A'                                                 // type Char
    val pi = 3.1415926                                          // type Double
    val b = true                                                // type Boolean
    println(" 1| i=$i, l=$l, c='$c', pi=$pi, b=$b")
}

/*======================================================================================================================
More on [Primitives]

Float, Short, Byte.
======================================================================================================================*/

fun moreOnPrimitives() {
    println("\n[More on Primitives]\n---")

    val e = 2.7182817F                                              // type Float
    val u = 53.toShort()                                            // type Short
    val b = 17.toByte()                                             // type Byte
    println(" 1| more primitives: e=$e, u=$u, b=$b")
    println(" 2| Kotlin class name 'u':'${u::class.simpleName}', Java class name:'${u::class.java.name}'")
}
