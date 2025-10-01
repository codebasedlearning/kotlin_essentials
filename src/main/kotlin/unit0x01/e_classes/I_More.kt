// (C) 2025 A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x01.e_classes

/*======================================================================================================================
Classes are a huge topic. We will start by looking at classes, properties, and methods.
Interfaces, overloads and more follows. We will also look at some sort of "static" behaviour.
======================================================================================================================*/

fun main() {
    println("Kotlin Essentials -> Classes | More")

    println("\n-- More --")
    moreOnOperatorOverloading()
    moreOnReflection()
}

/*======================================================================================================================
More on [Operator Overloading]

  Ref.:
  - https://kotlinlang.org/docs/reference/operator-overloading.html
======================================================================================================================*/

data class Modulo(val x: Int, val b: Int) {
    // a +op
    operator fun plus(increment: Int) = Modulo((x + increment) % b, b)
    override fun toString() = "$x"
}

// infix and extension function, returns 1 as Modulo-instance with base b
infix fun Int.basis(b: Int) = Modulo(1,b)

fun moreOnOperatorOverloading() {
    println("\n[More on Operator Overloading]\n---")

    val m = Modulo(3, 5)
    println(" 1| (${m.x}+4)%5=${m+4}")

    val one = 1 basis 5
    println(" 2| (1)%5=$one")
}

/*======================================================================================================================
More on [Reflection]

  Ref.:
  - https://kotlinlang.org/docs/reflection.html
======================================================================================================================*/
fun moreOnReflection() {
    println("\n[More on Reflection]\n---")

    val modClazz = Modulo::class
    println(" 1| Modulo.name='${modClazz.simpleName}'")

    //val m = modClazz.primaryConstructor?.call(3, 5)      // can be null
    //val n = m!! + 4                                      // !! means, can throw NPE
    //println("02) (${m.x}+4)%5=$n")
}
