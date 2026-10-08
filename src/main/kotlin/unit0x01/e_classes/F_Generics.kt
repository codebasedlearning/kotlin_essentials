// (C) A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

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
  - Constraints restrict the type parameter: '<T : Number>', or several via 'where'.
  - Variance (declaration-site): 'out T' if T is only returned (producer, covariant),
    'in T' if T is only taken (consumer, contravariant). Hence a List<Int> is a List<Number>
    ('List<out E>'), but a MutableList<Int> is not a MutableList<Number>.
  - Generics are erased at runtime on the JVM; 'inline' + 'reified' keeps the type available, see 'typeName'.
  - Star projection 'List<*>' means 'a list of some unknown type'.
  Ref.:
  - https://kotlinlang.org/docs/generics.html
======================================================================================================================*/

// class Boxed<T>(t: T) {
//    var value = t
// }
class Boxed<T>(var value: T)                                        // a generic class

fun <T> makeListFrom(item: T): List<T> = listOf(item)               // a generic function

fun <T : Number> inc(x: T) = x.toInt() + 1                          // a generic function with constraint on T

fun <T> maxOf3(a: T, b: T, c: T): T where T : Comparable<T> =    // constraint with 'where'
    maxOf(a, maxOf(b, c))

/*
open class Animal
class Dog : Animal()

out T — Covariance
    val dogs: Producer<Dog> = Producer { Dog() }
    val animals: Producer<Animal> = dogs  // OK
A producer of dogs can safely be used wherever a producer of animals is expected.

in T — Contravariance
    val animals: Consumer<Animal> = Consumer { println(it) }
    val dogs: Consumer<Dog> = animals  // OK
A consumer capable of accepting any animal can certainly accept dogs.
*/

fun interface Producer<out T> { fun produce(): T }                  // 'out': T is only returned (Covariance)
fun interface Consumer<in T> { fun consume(item: T) }               // 'in': T is only taken (Contravariance)


inline fun <reified T : Any> typeName() = T::class.simpleName             // T is known at runtime (reified)

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

    println(" 4| maxOf3(3,7,5)=${maxOf3(3, 7, 5)}, maxOf3('b','c','a')=${maxOf3("b", "c", "a")}")

    val ints: List<Int> = listOf(1, 2)
    val numbers: List<Number> = ints                                // ok, List<out E> is covariant
    // val mNumbers: MutableList<Number> = mutableListOf<Int>()     // error, MutableList<E> is invariant
    val produceInt = Producer { 42 }
    val produceNumber: Producer<Number> = produceInt                // ok, 'out'
    val consumeAny = Consumer<Any> { println(" 5| consumed '$it'") }
    val consumeString: Consumer<String> = consumeAny                // ok, 'in'
    consumeString.consume("text")
    println(" 6| numbers=$numbers, produced=${produceNumber.produce()}")

    val mixed: List<Any> = listOf(1, "a", 2.0, "b")
    println(" 7| typeName<Int>()=${typeName<Int>()}, strings=${mixed.filterIsInstance<String>()}")  // both use 'reified'
}
