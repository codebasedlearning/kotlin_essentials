// (C) A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

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

  - A data class comes with some built-in functionality: the compiler generates 'equals'/'hashCode',
    'toString', 'copy' and 'componentN' (for destructuring) from the properties of the primary constructor.
  - Properties declared in the class body are not part of this.
  - '==' calls 'equals' (structural equality), '===' checks for the same object (referential equality).
  - Prefer 'val' properties and 'copy' to keep data classes immutable.
  - Data classes are the natural partner of the library 'kotlinx.serialization': annotate them with
    '@Serializable' and convert them to/from JSON with 'Json.encodeToString(...)' / 'Json.decodeFromString(...)'.
  Ref.:
  - https://kotlinlang.org/docs/data-classes.html
======================================================================================================================*/

data class TeamMember(val name: String, val age: Int)

fun introduceDataClasses() {
    println("\n[Data Classes]\n---")

    val a5 = A5("22")
    println(" 1| a5.n=${a5.n}, a5=$a5")                             // no output/toString

    val paul = TeamMember(name = "Paul", age = 23)                  // data classes provide toString, copy and more
    println(" 2| paul: $paul")

    val olderPaul = paul.copy(age = 24)                             // a copy with changes, 'paul' is unchanged
    println(" 3| olderPaul: $olderPaul, paul: $paul")

    val paulAgain = TeamMember("Paul", 23)
    println(" 4| paul == paulAgain: ${paul == paulAgain}, paul === paulAgain: ${paul === paulAgain}")

    val (name, age) = paul                                          // destructuring via component1(), component2()
    println(" 5| name=$name, age=$age")

    val team = setOf(paul, paulAgain, olderPaul)                    // equals/hashCode: two different members only
    println(" 6| team: $team")
}
