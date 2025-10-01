// (C) 2025 A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x01.e_classes

import java.lang.RuntimeException

/*======================================================================================================================
From here it gets interesting...
======================================================================================================================*/

fun main() {
    println("Kotlin Essentials -> Classes | Extensions")

    introduceExtensionFunctions()
}

/*======================================================================================================================
[Extension Functions]

  - The 'receiver type' (e.g. 'MutableList<Int>') is the type/class being extended.
  - Inside, 'this' refers to the instance of the extended class ('receiver object').
  - Extensions are resolved statically, which means they are not virtual by receiver type.
  - If a class has a member function with same name, applicable to given arguments, the member wins.
  - MANY Kotlin libraries add functionality using extension functions.
  Ref.:
  - https://kotlinlang.org/docs/extensions.html
======================================================================================================================*/

fun String.first2(): String {
    return this.substring(0, 2)
}

fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]                                          // 'this' refers to the list
    this[index1] = this[index2]
    this[index2] = tmp
}

// generic extension function; as 'times' is not part of a common interface, we
// explicitly have to treat all types individually... not so nice, but this is
// more than Java is able to do, and in combination with inline and reified
// type erasure in JVM can be outsmarted
inline fun <reified T : Number> T.square() = when(T::class) {
    Int::class -> this.toInt()*this.toInt()
    Double::class -> this.toDouble()*this.toDouble()
    else -> throw RuntimeException("type not supported")
}

fun <T> Collection<T>.myForEach(n: Int = -1, block: (T) -> Unit): Collection<T> =
    apply { forEach { block(it) } } // or onEach { block(it) }
//{
//    this.forEach { block(it) }
//    return this
//}

fun introduceExtensionFunctions() {
    println("\n[Extension Functions]\n---")

    val s = "xyString"
    println(" 1| first 2 of '$s': '${s.first2()}'")

    val list = mutableListOf(1, 2, 3)
    list.swap(0, 2)
    println(" 2| swap(0, 2): $list")

    println(" 3| 12^2=${12.square()}, 1.5^2=${1.5.square()}")

    val list1 = listOf(1, 2, 3)
    print(" 4| forEach original:  ")
    list1.forEach { print("$it ") }
    println()

    // regular call
    print(" 5| myForEach(lambda): ")
    list1.myForEach(23, { x: Int -> print("$x ") })
    println()

    print(" 6| myForEach{lambda}: ")
    // move lambda out of call, use default n:int, 'it' as default name
    list1.myForEach { print("$it ") }
    println()

    // here, we also have this 'it' as default name for 'object to work with'
    val numbers = mutableListOf("one", "two", "three", "four", "five")

    println(" 7| ${numbers.map { it.length }.filter { it > 3 }}")
}
