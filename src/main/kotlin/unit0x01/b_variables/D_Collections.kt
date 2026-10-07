// (C) A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x01.b_variables

/*======================================================================================================================
This snippet is about collection types.
======================================================================================================================*/

fun main() {
    println("Kotlin Essentials -> Variables | Collections")

    introduceCollections()

    println("\n-- More --")
    moreOnCollections()
}

/*======================================================================================================================
[Collections]

Working with mutable and non-mutable (read-only) collections.
  - Basically we have lists, sets and maps, and each collection type can be mutable or read-only.
  - Almost always prefer a List over an Array, unless you have low-level or performance requirements or need
    Java interop (e.g. 'main(args: Array<String>)', varargs). A List can be read-only, an Array is always mutable.
  - For arrays of primitive types (IntArray, ...) see 'More'.
  - Note: 'listOf', 'setOf' and 'mapOf' return read-only views, not truly immutable objects (a cast to
    'MutableList' would even allow 'set'). For real immutability see the library 'kotlinx.collections.immutable'.
  - 'setOf' and 'mapOf' (and their mutable versions) keep the insertion order (LinkedHashSet/LinkedHashMap).
  More:
  - Typical operations on lists, sets and maps can be found here.
  - Try to stick to the non-mutable parts of them.
  - Kotlin 2.4 brings 'isSorted()' (stable) and, experimental (-Xcollection-literals), collection literals:
    'val l: List<Int> = [1, 2, 3]'.
  Ref.:
  - https://kotlinlang.org/docs/kotlin-tour-collections.html
  - Definitions in Collections.kt, Sets.kt, Maps.kt
======================================================================================================================*/
fun introduceCollections() {
    println("\n[Collections]\n---")

    val imList = listOf(1, 2, 3)                                // type List<Int>, read-only, no add
    val mList = mutableListOf("1", "2")                         // type MutableList<String>
    mList.add("3")                                              // note 'val (!) mList' and mList is modified
    println(" 1| imList: $imList, mList: $mList")

    val imSet = setOf(1, 2, 3, 2)                               // read-only set, note: only one '2'-element
    val mSet = mutableSetOf(1, 2)                               // mutable set
    mSet.add(3)
    mSet.add(2)
    println(" 2| imSet: $imSet, mSet: $mSet")

    val imMap = mapOf("one" to 1, "two" to 2)                   // read-only map (keeps insertion order)
    val mMap = mutableMapOf("one" to 1, "two" to 2)             // mutable map (a LinkedHashMap)
    mMap["three"] = 3                                           // same as map.put
    mMap["one"] = 11
    println(" 3| imMap: $imMap, mMap: $mMap")

    val a = arrayOf(1, 2, 3)                                    // type Array, fixed size, and mutable
    a[0] = 3
    println(" 4| array: [${a.joinToString(",")}]")              // $a gives a ref. but no content, hence joinToString
                                                                // or a.contentToString()
}

/*======================================================================================================================
More on [Collections]

Common operations on collections.
======================================================================================================================*/
fun moreOnCollections() {
    println("\n[More on Collections]\n---")

    // MutableList -> List, MutableCollection
    val mList = mutableListOf("1", "2")
    mList.add("3")                                              // mList += "3"
    mList.remove("1")
    mList.removeAt(0)

    val indexList = mList.indexOf("2")
    val containsList = "3" in mList                             // mList.contains("3")
    println(" 1| mList:$mList, index:$indexList, contains:$containsList")

    mList.clear()
    mList.addAll(listOf("4","5"))
    println(" 2| mList:$mList")

    // MutableSet -> Set, MutableCollection
    val mSet = mutableSetOf(1, 2)
    mSet += 3                                                   // mSet.add(3)
    mSet.remove(1)
    val containsSet = 3 in mSet                                 // mSet.contains(3)
    println(" 3| mSet:$mSet, contains:$containsSet")

    mSet.clear()
    mSet.addAll(setOf(4,5))
    println(" 4| mSet:$mSet")

    // MutableMap -> Map
    val mMap = mutableMapOf("one" to 1, "two" to 2)
    mMap["three"] = 3                                           // mMap.put("three",3)
    mMap.remove("one")
    val containsMap = "two" in mMap
    val containsValue = mMap.containsValue(1)
    println(" 5| mMap:$mMap, contains:$containsMap, value:$containsValue")

    mMap.clear()
    mMap.putAll(mapOf("four" to 4))

    println(" 6| mMap:$mMap")

    fun printMap(map: Map<String,Int>) = println(" 7| map:$map")    // preview: local fct., read-only view
    printMap(mMap)

    // arrays of primitive types: no boxing, like int[] in Java
    val ints = intArrayOf(3, 1, 2)                              // IntArray; also LongArray, DoubleArray, BooleanArray, ...
    val zeros = IntArray(3)                                     // [0, 0, 0]
    println(" 8| ints:${ints.contentToString()}, sorted:${ints.isSorted()}, zeros:${zeros.contentToString()}")
    ints.sort()                                                 // in-place, arrays are mutable
    println(" 9| ints:${ints.contentToString()}, sorted:${ints.isSorted()}")  // 'isSorted' is new in Kotlin 2.4

    val squares = buildList {                                   // build a read-only list with a mutable builder
        for (i in 1..4) add(i * i)
    }
    println("10| squares:$squares, sortedDescending:${squares.isSortedDescending()}")
}
