// (C) 2025 A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x01.b_variables

/*======================================================================================================================
In this snippet we discuss Kotlin's treatment and ideas about null references and nullable types.
======================================================================================================================*/

fun main() {
    println("Kotlin Essentials -> Variables | Nullables")

    introduceNullables()
    introduceSmartCasts()

    println("\n-- More --")
    moreOnNullables()
}

/*======================================================================================================================
[Nullables]

Understand nullables technically, but also their implications for safe programming.
  - Kotlin's type system is aimed at eliminating the danger of null references, also known
    as 'The Billion-Dollar Mistake'. It tries to avoid null-ref. exceptions by following the code paths to
    what can be null and what is not null for sure.
  - Each type can be equipped with '?' - meaning that variables of this type can be null.
    Conversely, no "?", no null. This is different to Java.
  - The combination of ?. and ?: is used VERY often.
  - Operator '?.' performs a safe call (calls a method or accesses a property if the receiver is non-null).
    Otherwise, the result of the expression is still null.
  - Operator '?:' takes the right-hand value if the left-hand value is null (elvis operator).
  - Operator "!!" asserts that an expression is non-null and gives the non-null type.
  Ref.:
  - https://kotlinlang.org/docs/keyword-reference.html#special-identifiers
  - https://kotlinlang.org/docs/null-safety.html
======================================================================================================================*/
fun introduceNullables() {
    println("\n[Nullables]\n---")

    var s1 = "hello"                                                // 's1' of type 'String' is not allowed to be null
    // s1 = null                                                    // does not work
    var s2: String? = null                                          // for type 'String?' it is ok
    val s3 = s2 ?: s1                                               // Elvis operator (hair/pompadour), takes the
    println("1 | s3='$s3'")                                         // right-hand value if the left-hand value is null

    var i1: Int? = 42                                               // i1 is 42 but can be null
    i1 = null
    val i2 = i1 ?: 1
    println("2 | i1=$i1, i2=$i2")

    val list = if (i2 > 0) listOf(1, 2, 3) else null                // preview: if-expression, type is 'List?'
    val len = list?.size ?: 0                                       // ?. means: use op'.' if non-null-reference,
    println("3 | len=$len")                                         // otherwise expression is null -> hence Elvis-op

    println("4 | list!!=${list!!}")                                 // convert to a non-null type (be absolute sure)
}

/*======================================================================================================================
[Nullables Handling]

Understanding and use smart-casts of nullable types.
======================================================================================================================*/
fun introduceSmartCasts() {
    println("\n[Smart-Casts: Nullables Handling]\n---")

    var list: List<Int>? // = null                              // default
    list = listOf(1, 2, 3)
    if (list != null) {
        println("1 | list.size=${list.size}")                   // smart-cast: list is not-null here, no need for ?.
    }
}

/*======================================================================================================================
More on [Nullables]
  - If the compiler recognises code that can be guaranteed not to be null, additional tests for null,
    e.g. via '?.', are not necessary.
  Ref.:
  - https://kotlinlang.org/docs/null-safety.html
======================================================================================================================*/
fun moreOnNullables() {
    println("\n[More on Nullables]\n---")

    val list = listOf(1, null, 2, null, 3)
    println(" 1| list=$list, size:${list.size}")
    for (n in list) {                                           // preview: 'for'
        n ?: continue                                           // means: if null continue
        println("  | n=$n")
    }
}
