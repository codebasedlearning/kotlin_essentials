// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

package unit0x01.e_classes

/*======================================================================================================================
Parametric Polymorphism
======================================================================================================================*/

fun main() {
    println("Kotlin Essentials -> Classes | Generics")

    introduceGenerics()
}

/*======================================================================================================================
[Generics]

Defining generic classes and functions.
  Ref.:
  - https://kotlinlang.org/docs/generics.html
======================================================================================================================*/

// class Boxed<T>(t: T) {
//    var value = t
// }
class Boxed<T>(var value: T)                                        // a generic class

fun <T> makeListFrom(item: T): List<T> = listOf(item)               // a generic function

fun <T : Number> inc(x: T) = x.toInt() + 1                          // a generic function with constraint on T

fun hasAbcPrefix(x: Any) = when (x) {                               // some sort of 'generic function' 'Any' is any type
    is String -> x.startsWith("abc")
    else -> false
}

fun introduceGenerics() {
    println("\n[Generics]\n---")

    val box1: Boxed<Int> = Boxed(1)        // Boxed<Int>(1)
    val box2 = Boxed(2)

    println(" 1| box1:${box1.value}, box2:${box2.value}")

    val list3 = makeListFrom(3)
    println(" 2| list3:$list3, ${inc(1)}")

    println(" 3| abcPrefix('abcd'): ${hasAbcPrefix("abcd")}, abcPrefix(1): ${hasAbcPrefix(1)}")
}
