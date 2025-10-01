// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

package unit0x01.b_variables

/*======================================================================================================================
This snippet is about declaring and defining variables.
======================================================================================================================*/

private const val topic = "Val and Var"                         // constant (!) value on top-level (compile-time)

fun main() {
    println("\nKotlin Essentials -> Variables | $topic")

    introduceValAndVar()
}

/*======================================================================================================================
[Val and Var]

Define mutable and read-only variables.
  - 'var': mutable variable.
  - 'val': variable assigned once at runtime. Sometimes this is called 'read-only', but don't get confused, a collection
    declared with 'val' can be modified, see 'list'. If possible, use 'val'.
  - 'const' declares a compile-time constant, type must be primitive or String; thus it can be inlined
  - Note that even if no type information is given (or inferred), all variables or objects have a type.
    Kotlin is strictly typed. In most cases, specifying the type explicitly is not necessary and should be avoided.
  - Modifiers at file level (e.g. 'topic'):
        'public'=visible everywhere (default)
        'private'=visible in file
        'internal'=visible in module
  - Follow the IDE's suggestions.
  More:
  - Global variables declared at the top level of a Kotlin file are not really global, but part of a class
    that Kotlin creates named after the file+'Kt'.
    This way they are addressable from Java, for example: package.snippetKt.the_answer (if it were public).
  Ref.:
  - https://kotlinlang.org/docs/basic-types.html
  - https://kotlinlang.org/docs/visibility-modifiers.html#packages
  - https://kotlinlang.org/docs/java-to-kotlin-interop.html
======================================================================================================================*/

fun introduceValAndVar() {
    println("\n[Val and Var]\n---")

    val counter = 9                                             // val: read-only variable, assigned once at runtime
    // val counter: Int = 9                                     // same with type info
    var next = counter                                          // var: mutable variable, type is inferred
    ++next

    println(" 1| counter=$counter, next=$next")

    val list = mutableListOf(1,2,3)                             // preview: list; the reference cannot be reassigned,
    list.add(4)                                                 // but the object itself can be modified
    println(" 2| list=$list")
}
