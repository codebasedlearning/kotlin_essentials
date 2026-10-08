// (C) A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x01.c_control_flow

/*======================================================================================================================
Obviously we need structural elements to control the program flow. That is what the snippet is for.
======================================================================================================================*/

fun main() {
    println("Kotlin Essentials -> Control Flow | Conditionals")

    introduceIf()
    introduceWhen()

    println("\n-- More --")
    moreOnWhen()
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
[When]

Working with 'when' (aka switch-case).
  - 'when' is like 'switch-case' with cases more complex.
  - There is no fall-through, the first matching branch wins.
  - Used as an expression, 'when' must be exhaustive (usually via 'else').
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

/*======================================================================================================================
More on [When]

The real power of 'when'.
  - Without a subject, 'when' replaces if-else-if chains.
  - With 'is' you check types, and the subject is smart-cast in the branch.
  - For sealed types (and enums) the compiler knows all cases, so no 'else' is needed - and if you add
    a new subtype later, the compiler shows you every 'when' you have to adapt.
  - Guard conditions (Kotlin 2.2): 'is Circle if shape.radius == 0.0 ->' adds a condition to a branch.
    (Basically this is an &&, but think of the syntax explicitly separates matching from guarding.)
  - Data-flow based exhaustiveness (Kotlin 2.3): cases excluded before (e.g. by an early return) are not
    required anymore.
  Ref.:
  - https://kotlinlang.org/docs/control-flow.html#guard-conditions-in-when-expressions
  - https://kotlinlang.org/docs/sealed-classes.html#use-sealed-classes-with-when-expression
======================================================================================================================*/

sealed interface Shape                                          // all subtypes are known at compile time
data class Circle(val radius: Double) : Shape
data class Rect(val width: Double, val height: Double) : Shape
data object Dot : Shape                                         // a singleton with a nice 'toString'

fun moreOnWhen() {
    println("\n[More on When]\n---")

    val x = 7
    when {                                                      // no subject: replaces if-else-if chains
        x < 0 -> println(" 1| $x is negative")
        x % 2 == 0 -> println(" 1| $x is even")
        else -> println(" 1| $x is odd")
    }

    fun describe(shape: Shape) = when (shape) {                 // sealed: exhaustive without 'else'
        is Circle if shape.radius == 0.0 -> "a point-like circle"   // guard condition (Kotlin 2.2)
        is Circle -> "circle, r=${shape.radius}"                // smart-cast to Circle
        is Rect if shape.width == shape.height -> "square, a=${shape.width}"
        is Rect -> "rect, ${shape.width}x${shape.height}"
        Dot -> "just a $shape"
    }
    listOf(Circle(0.0), Circle(1.5), Rect(2.0, 2.0), Rect(2.0, 3.0), Dot).forEach {
        println(" 2| ${describe(it)}")
    }

    fun area(shape: Shape): Double {
        if (shape is Dot) return 0.0                            // data-flow based exhaustiveness (Kotlin 2.3):
        return when (shape) {                                   // the compiler knows 'shape' cannot be 'Dot' here
            is Circle -> Math.PI * shape.radius * shape.radius
            is Rect -> shape.width * shape.height
        }
    }
    println(" 3| area(Rect(2,3))=${area(Rect(2.0, 3.0))}, area(Dot)=${area(Dot)}")
}
