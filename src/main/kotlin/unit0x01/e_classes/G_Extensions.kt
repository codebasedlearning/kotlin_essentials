// (C) A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x01.e_classes

/*======================================================================================================================
From here it gets interesting...
======================================================================================================================*/

fun main() {
    println("Kotlin Essentials -> Classes | Extensions")

    introduceExtensionFunctions()
    introduceContextParameters()
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
    return this.substring(0, 2)                                     // crashes for shorter strings, 'take(2)' would not
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
    else -> throw IllegalArgumentException("type not supported")
}

// 'n' limits the number of processed elements, -1 means all
fun <T> Collection<T>.myForEach(n: Int = -1, block: (T) -> Unit): Collection<T> =
    apply { (if (n < 0) this else take(n)).forEach { block(it) } } // for all elements simply: onEach { block(it) }

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
    print(" 5| myForEach(2, lambda): ")
    list1.myForEach(2, { x: Int -> print("$x ") })
    println()

    print(" 6| myForEach{lambda}: ")
    // move lambda out of call, use default n=-1, 'it' as default name
    list1.myForEach { print("$it ") }
    println()

    // here, we also have this 'it' as default name for 'object to work with'
    val numbers = mutableListOf("one", "two", "three", "four", "five")

    println(" 7| ${numbers.map { it.length }.filter { it > 3 }}")
}

/*======================================================================================================================
[Context Parameters]

  - An extension function has exactly one receiver ('this'). Context parameters (stable since Kotlin 2.4) let a
    function require further values from the calling context, e.g. a logger, a transaction or a coroutine scope.
  - The caller provides them implicitly, e.g. via 'with(logger) { ... }' or the stdlib function 'context(logger) { ... }'.
  - They replace the former experimental 'context receivers'.
  Ref.:
  - https://kotlinlang.org/docs/context-parameters.html
======================================================================================================================*/

class Logger(private val prefix: String) {
    fun log(message: String) = println("$prefix $message")
}

context(logger: Logger)                                             // requires a Logger in the calling context
fun Int.timesTen(): Int {                                           // ... and is an extension function, too
    logger.log(". timesTen($this)")
    return this * 10
}

fun introduceContextParameters() {
    println("\n[Context Parameters]\n---")

    with(Logger(" a|")) {                                           // a Logger is now in the context
        println(" 1| 4.timesTen()=${4.timesTen()}")
    }
    context(Logger(" b|")) {                                        // the same, more explicit
        println(" 2| 5.timesTen()=${5.timesTen()}")
    }
    // 6.timesTen()                                                 // error, no Logger in the context
}
