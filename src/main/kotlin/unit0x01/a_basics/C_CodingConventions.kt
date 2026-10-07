// (C) A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x01.a_basics

/*======================================================================================================================
Write your code in the right style from the start. Let's take a look at some common coding conventions,
even if they are introduced later.
======================================================================================================================*/

fun main() {
    println("\nKotlin Essentials -> Basics | Conventions")

    viewCodingConventions()
}

/*======================================================================================================================
[Coding Conventions]

Be familiar with the basic naming conventions.
  - Be concise and consistent.
  Ref.:
  - https://kotlinlang.org/docs/coding-conventions.html
======================================================================================================================*/

fun viewCodingConventions() {
    println("\n[Common Coding Conventions]\n---")
    println("""
         ${" 1"}| Naming
            - packages use lowercase, no underscores ('org.example.project')
              (this course breaks the rule on purpose: 'unit0x01.a_basics' keeps the snippets sorted)
            - functions, properties and local variables use camel case, no underscores, starting lowercase ('processData')
            - classes and objects use camel case, starting uppercase ('InputData')
            - constants ('const val', or top-level/object 'val' holding deeply immutable data) use
              uppercase underscore-separated names (screaming snake case) ('MAX_COUNT')
            - backing properties, i.e. a private property behind a public one, use an underscore prefix
              ('private val _elements' behind 'val elements'); other private properties do not
              (since Kotlin 2.4 'explicit backing fields' often replace this pattern, see 'Properties')
         ${" 2"}| Class layout
            - Property declarations and initializer blocks
            - Secondary constructors
            - Method declarations
            - Companion object
            - implement members in the same order as in the interface
         ${" 3"}| Rules
            - Simple read-only properties in one line, otherwise getter and setter on separate lines
            - Omit semicolons whenever possible
            - Prefer using expression bodies
            - Use named arguments for Boolean or same-typed parameters, unless the meaning is clear from context
        """.trimIndent())
}
