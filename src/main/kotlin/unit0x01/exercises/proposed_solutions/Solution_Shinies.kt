// (C) 2025 A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x01.exercises.proposed_solutions

private fun main() {
    println("\nProposed solution 'Shinies'\n--")
    solution()
}

private fun <T> List<T>.compress(
    delimiter:String=",",
    transform: (T) -> String = { it.toString() }): String
{
    //    var s = ""
    //    for ((index1, value1) in this.withIndex()) {
    //       s = if (index1>0) "$s$delimiter${value1}" else "${value1}"
    //    }

    // (use 'reduce' for the same type)
    //    val s = this.withIndex().fold("") { accumulator, current ->
    //       if (current.index>0) "$accumulator$delimiter${current.value.toString()}" else current.value.toString()
    //    }

    //    var s = if (isEmpty()) "" else this[0].toString()
    //    for (index2 in 1..<size) {
    //        s = "$s$delimiter${this[index2].toString()}"
    //    }

    // use StringBuffer if you need thread-safety
    val sb = StringBuilder("[")
    if (isNotEmpty())
        sb.append(transform(this[0]))
    for (i in 1..<size) {
        sb.append(delimiter)
        sb.append(transform(this[i]))
    }
    sb.append("]")
    return sb.toString()
}

private fun solution() {
    println("0 | join ${listOf(1,2,3).joinToString(",") {it.toString()}}\n")

    listOf<Int>().compress().also {
        println("1 | $it")
        require(it=="[]")
    }
    listOf(1).compress().also {
        println("2 | $it")
        require(it=="[1]")
    }
    listOf(1,2,3).compress().also {
        println("3 | $it")
        require(it=="[1,2,3]")
    }
    listOf(1,2,3).compress { "'$it'"}.also {
        println("4 | $it")
        require(it=="['1','2','3']")
    }

    listOf(1.1,2.2,3.3).compress().also {
        println("5 | $it")
        require(it=="[1.1,2.2,3.3]")
    }
}
