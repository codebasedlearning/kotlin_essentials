// (C) 2025 A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x01.exercises.proposed_solutions

private fun main() {
    println("\nProposed solution 'Pummels'\n--")
    solution()
}

private fun solution() {
    for (n in listOf(1,2,5,10)) {
        println("fib_$n: ${fibonacciIteratively(n)}, ${fibonacciRecursively(n)}")
    }
    println("fibMap=$fibMap")
}

private fun fibonacciIteratively(n: Int): Int {
    if (n <= 1) {
        return n
    }
    var fib = 1
    var fib0 = 1

    for (i in 2 until n) {
        val temp = fib
        fib += fib0
        fib0 = temp
    }
    return fib
}

// wrong for negative numbers
// private fun fibonacciRecursively(n: Int): Int =
//    if (n <= 1) { n } else { fibonacciRecursively(n - 1) + fibonacciRecursively(n - 2) }

// with when-expression
// private fun fibonacciRecursively(n: Int): Int = when {
//    n < 0 -> throw RuntimeException("n negative")
//    n <=1 -> n
//    else -> fibonacciRecursively(n - 1) + fibonacciRecursively(n - 2)
//}

private val fibMap = mutableMapOf(0 to 0, 1 to 1)

private fun fibonacciRecursively(n: Int): Int {
    if (n<0) throw RuntimeException("n negative")
    var fib = fibMap[n]                                             // avoid 'contains' with '[]'; it's twice the query
    if (fib!=null)                                                  // fibMap[n]?.let { return it }
        return fib
    fib = fibonacciRecursively(n - 1) + fibonacciRecursively(n - 2)
    fibMap[n] = fib
    return fib
}

// with scope-function
// private fun fibonacciRecursively(n: Int): Int {
//    if (n<0) throw RuntimeException("n negative")
//    fibMap[n]?.let { return it }
//    return (fibonacciRecursively(n - 1) + fibonacciRecursively(n - 2)).also {
//        fibMap[n] = it
//    }
//}
