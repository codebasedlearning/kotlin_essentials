// (C) A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

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

  - Kotlin has no 'static'. Use top-level declarations or a companion object instead.
  - Class members can access the private members of the corresponding companion object (and vice versa).
  - The name of a class used by itself acts as a reference to the companion object of the class.
    Note that even though the members of companion objects look like static members in other languages,
    at runtime those are still instance members of real objects, and can, for example, implement interfaces.
  - If Java code should see real static members, use '@JvmStatic', '@JvmField' or 'const'.
  Ref.:
  - https://kotlinlang.org/docs/object-declarations.html#companion-objects
======================================================================================================================*/

class C1 private constructor(val id: Int) {                       // private ctor: instances via the factory only
    companion object Factory {
        const val MAX_ID = 99                                       // a constant, like 'static final' in Java
        private var nextId = 1                                      // shared state, like a 'static' field

        fun create(): C1 = C1(nextId++.coerceAtMost(MAX_ID))        // may call the private constructor
    }

    override fun toString() = "C1(id=$id)"
}

fun introduceCompanionObjects() {
    println("\n[Companion Object]\n---")

    // val c = C1(42)                                               // error, the constructor is private
    val a = C1.create()                                             // via the class name
    val b = C1.Factory.create()                                     // or explicitly via the companion's name
    println(" 1| a=$a, b=$b, MAX_ID=${C1.MAX_ID}")
}
