// (C) 2025 A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x01.e_classes

/*======================================================================================================================
Classes are a huge topic. We will start by looking at classes, properties, and methods.
Interfaces, overloads and more follows. We will also look at some sort of "static" behaviour.
======================================================================================================================*/

fun main() {
    println("Kotlin Essentials -> Classes | Companion Objects")

    introduceCompanionObjects()
}

/*======================================================================================================================
[Companion Object]

  - Class members can access the private members of the corresponding companion object.
  - The name of a class used by itself acts as a reference to the companion object of the class.
    Note that even though the members of companion objects look like static members in other languages,
    at runtime those are still instance members of real objects, and can, for example, implement interfaces.
  Ref.:
  - https://kotlinlang.org/docs/object-declarations.html#semantic-difference-between-object-expressions-and-declarations
======================================================================================================================*/

class C1 {
    companion object Factory {
        fun create(): C1 = C1()
    }
}

fun introduceCompanionObjects() {
    println("\n[Companion Object]\n---")

    val instance = C1.create()
}
