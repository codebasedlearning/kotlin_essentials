// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

package unit0x01.e_classes

/*======================================================================================================================
Classes are a huge topic. We will start by looking at classes, properties, and methods.
Interfaces, overloads and more follows. We will also look at some sort of "static" behaviour.
======================================================================================================================*/

fun main() {
    println("Kotlin Essentials -> Classes | Data Classes")

    introduceDataClasses()
}

/*======================================================================================================================
[Data Classes]

  - A data class comes with some built-in functionality.
  Ref.:
  - https://kotlinlang.org/docs/data-classes.html
======================================================================================================================*/

data class TeamMember(val name: String, val age: Int)

fun introduceDataClasses() {
    println("\n[Data Classes]\n--")

    val a5 = A5("22")
    println(" 1| a5.n=${a5.n}, a5=$a5")                             // no output/toString

    val paul = TeamMember(name = "Paul", age = 23)                  // data classes provide toString, copy and more
    println(" 2| paul: $paul")
}
