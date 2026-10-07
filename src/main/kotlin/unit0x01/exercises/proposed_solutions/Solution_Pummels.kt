// (C) A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x01.exercises.proposed_solutions

private fun main() {
    println("\nProposed solution 'Pummels'\n--")
    solution()
}

private fun solution() {
    for (n in listOf(1,2,5,10,50)) {
        println("fib_$n: ${fibonacciIteratively(n)}, ${fibonacciRecursively(n)}")
    }
    println("fibMap: ${fibMap.size} cached values, e.g. fib_47=${fibMap[47]} (> Int.MAX_VALUE=${Int.MAX_VALUE})")
}

// note: we use Long, as Int overflows silently from fib(47) on (and Long from fib(93) on, then use BigInteger)
private fun fibonacciIteratively(n: Int): Long {
    if (n <= 1) {
        return n.toLong()
    }
    var fib = 1L
    var fib0 = 1L

    for (i in 2 until n) {
        val temp = fib
        fib += fib0
        fib0 = temp
    }
    return fib
}

// wrong for negative numbers
// private fun fibonacciRecursively(n: Int): Long =
//    if (n <= 1) { n.toLong() } else { fibonacciRecursively(n - 1) + fibonacciRecursively(n - 2) }

// with when-expression
// private fun fibonacciRecursively(n: Int): Long = when {
//    n < 0 -> throw IllegalArgumentException("n negative")
//    n <=1 -> n.toLong()
//    else -> fibonacciRecursively(n - 1) + fibonacciRecursively(n - 2)
//}

private val fibMap = mutableMapOf(0 to 0L, 1 to 1L)

private fun fibonacciRecursively(n: Int): Long {
    require(n >= 0) { "n negative" }                                // throws IllegalArgumentException
    var fib = fibMap[n]                                             // avoid 'contains' with '[]'; it's twice the query
    if (fib!=null)                                                  // fibMap[n]?.let { return it }
        return fib
    fib = fibonacciRecursively(n - 1) + fibonacciRecursively(n - 2)
    fibMap[n] = fib
    return fib
}

// with scope-function
// private fun fibonacciRecursively(n: Int): Long {
//    require(n >= 0) { "n negative" }
//    fibMap[n]?.let { return it }
//    return (fibonacciRecursively(n - 1) + fibonacciRecursively(n - 2)).also {
//        fibMap[n] = it
//    }
//}

// even shorter with 'getOrPut'
// private fun fibonacciRecursively(n: Int): Long {
//    require(n >= 0) { "n negative" }
//    return fibMap.getOrPut(n) { fibonacciRecursively(n - 1) + fibonacciRecursively(n - 2) }
//}
