// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

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
            - functions, properties and local variables use camel case, no underscores, starting lowercase ('processData')
            - classes use camel case, starting uppercase ('InputData')
            - top-level or properties use camel case names ('connectedDevices')
            - private properties or implementation details use underscore as prefix ('_defaultConnection')
            - constants use uppercase underscore-separated (screaming snake case) ('MAX_COUNT')
         ${" 2"}| Class layout
            - Property declarations and initializer blocks
              Simple read-only properties in one line, otherwise set and get separate lines")
            - Method declarations
            - Companion object
            - implement members same order as interface
         ${" 3"}| Rules
            - Omit semicolons whenever possible
            - Prefer using expression bodies
            - Use named argument syntax for parameters unless meaning is clear from context
        """.trimIndent())
}
