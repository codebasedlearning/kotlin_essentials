// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

package unit0x02.a_sequences

/*======================================================================================================================
This snippet introduces
- sequences, a type of collection that can be thought of as a more generalised form of lists or arrays, and
- yield, a function used in sequence generators;
- and, finally, the idea of suspension points.
======================================================================================================================*/

fun main() {
    println("Kotlin Essentials -> Sequences | Yield")

    discussDataHandling()
    introduceSequences()
    viewListVsSequence()
    viewSequencesAtWork()

    println("\n-- More --")
    viewSequenceImplementation()
    moreOnUnlimitedSequences()
    moreOnYieldAll()
}

/*======================================================================================================================
[Data Handling]

Motivates a better, more efficient approach to handling sequence data - hopefully.
======================================================================================================================*/
fun discussDataHandling() {
    println("\n[Data Handling]\n---")

    // Creates a list of powers, e.g., 2,4,8,16.
    fun powersOf(base: Int, maxExponent: Int) = mutableListOf<Int>().apply {
        var result = 1
        repeat(maxExponent) {                                   // for (i in 0..< maxExponent)
            result *= base
            add(result)
        }
    }

    // Q: What is the 'problem' here? Think large and in terms of resources...
    val powersOf2 = powersOf(base=2, maxExponent=4)
    print("1 | from list: ")
    for (n in powersOf2) {
        print("n=$n ")
    }
    println()

    // Q: What do we need to have a 'better', more efficient, way of dealing with such data?
}

/*======================================================================================================================
[First Sequence]

A suspended (paused) function - that's new.
  - The important thing to understand in this snippet is that 'yield' is in a sense 'returning' a value, but leaving
    the function active in order to continue after the yield, e.g. in a for loop.
  - Under the hood we have an iterator concept that looks for a 'next value' and as long as there is data
    the iterator is not finished. -> see More
  - Suspending a function essentially means having 'suspension points', where a function can be left and
    resumed after a while. So this concept is similar to suspension functions, but the context and usage is different.
  Ref.:
  - https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.sequences/
  - https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.sequences/-sequence-scope/yield.html
  - https://www.baeldung.com/kotlin/yield-function
======================================================================================================================*/
fun introduceSequences() {
    println("\n[Sequences]\n---")

    fun createSequence135() = sequence {                        // generator function
        println(" a| . start seq, before yield 1")
        yield(1)                                                // function is 'paused' and later 'resumed'
        println(" b| . before yield 3")
        yield(3)
        println(" c| . before yield 5")
        yield(5)
        println(" d| . end seq")
    }
    println(" 1| obtain sequence")
    val seq = createSequence135()                               // note: code in sequence is not executed -> lazy
    println(" 2| start for")
    for (n in seq) {
        println(" 3| n=$n ")
    }
    println(" 4| end for")
}

/*======================================================================================================================
[List vs. Sequence]

Looking for a way to iterate a sequence of values without storing them.
  - In particular, this code shows: (a) why and where sequences can be used, and (b) the similarity on
    the algorithmic side, pointing out the place where building the data structure can be replaced
    by providing a value with 'yield'.
======================================================================================================================*/
fun viewListVsSequence() {
    println("\n[List vs. Sequence]\n---")

    // as before
    fun powersOfList(base: Int, maxExponent: Int) = mutableListOf<Int>().apply {
        var result = 1
        repeat(maxExponent) {
            result *= base
            add(result)
        }
    }

    print("1 | from list: ")
    for (n in powersOfList(base=2, maxExponent=4)) {
        print("n=$n ")
    }
    println()

    // Create a 'sequence', but unlike the list above, it is lazily evaluated, i.e., calculations are performed
    // only when required. Notice the 'yield', the function that returns the value one at a time.
    fun powersOfSequence(baseNumber: Int, maxExponent: Int) = sequence {
        var result = 1
        repeat(maxExponent) {
            result *= baseNumber
            yield(result)                                       // pause here; this is the 'add' before
        }
    }

    print("2 | from seq.: ")
    for (n in powersOfSequence(baseNumber=2, maxExponent=4)) {
        print("n=$n ")
    }
    println()
}

/*======================================================================================================================
[Sequences at work]

Sequences often require follow-up operations such as filtering or mapping.
  - It is worth looking at the definition of 'map', for example. The iterator concept is clearly visible.
    So 'map' and 'filter' are also lazy.
  Ref.:
  - https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.sequences/
  - https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.sequences/filter.html
  - https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.sequences/map.html
======================================================================================================================*/
fun viewSequencesAtWork() {
    println("\n[Sequences at work]\n---")

    val sequence1357 = sequence {
        yield(1)
        yield(3)
        yield(5)
        yield(7)
    }

    // print("1 | sequence1357:")                               // use joinToString instead
    // for (n in sequence1357) {
    //     print(" $n")
    // }
    // println()
    println("1 | sequence1357: ${sequence1357.joinToString(" ")}")

    val sequence46 = sequence1357
        .map { it+1 }                                           // take a look at the definition
        .filter { it>2 }
        .take(2)
    println("2 | sequence68:   ${sequence46.joinToString(" ")}")
}

/*======================================================================================================================
More on [Sequence Implementation]

  - Under the hood we have an iterator concept that looks for a 'next value' and as long as there is data
    the iterator is not finished.
======================================================================================================================*/
fun viewSequenceImplementation() {
    println("\n[More on Sequence Implementation]\n---")

    fun createSequence159() = sequence {                        // generator function
        println(" a| . start seq, before yield 1")
        yield(1)                                                // function is 'paused' and later 'resumed'
        println(" b| . before yield 5")
        yield(5)
        println(" c| . before yield 9")
        yield(9)
        println(" d| . end seq")
    }

    println(" 1| obtain sequence iterator")
    val iter = createSequence159().iterator()                   // this is how it works:
    println(" 2| start while")                                  //   sequence implements an 'iterator'
    while (iter.hasNext()) {                                    // starts/continues sequence
        println(" 3| in loop")                                  //   and an iterator implements 'next' and 'hasNext'
        val n = iter.next()
        println(" 4| n=${n} ")
    }
    println(" 5| end while")
}

/*======================================================================================================================
More on [Unlimited sequences]

Are there infinite sequences? Yes!
  - It is perfectly valid to have an infinite or 'unlimited' sequence. You just need to limit each traverse
    to a finite amount of data to avoid infinite loops.
  Ref.:
  - https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.sequences/take.html
======================================================================================================================*/
fun moreOnUnlimitedSequences() {
    println("\n[Unlimited sequences]\n---")

    val unlimitedSequence = generateSequence(1) { it*2 }            // x:=f(x), x0=seed(here 1)

    print("1 | unlimited sequence, with take 5:   ")
    for (i in unlimitedSequence.take(5)) {                          // We can only process a finite number of values.
        print("n=$i ")
    }
    println()
}

/*======================================================================================================================
More on [YieldAll]

Composing sequences.
  - Instead of having a for-loop for each sequence, YieldAll basically does this.
  Ref.:
  - https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.sequences/-sequence-scope/yield-all.html
======================================================================================================================*/
fun moreOnYieldAll() {
    println("\n[More on YieldAll]\n---")

    val list123 = listOf(1, 2, 3)
    val list567 = listOf(5, 6, 7)

    val sequenceBoth = sequence {
        yieldAll(list123)                                           // appends all from list123,
        yield(44)                                                   // then a single value,
        yieldAll(list567)                                           // then from list567,
        yield(88)                                                   // again a single value,
        yieldAll(generateSequence(9) { it+1 })                      // and finally, unlimited, from a generated sequence
    }

    print("1 | combine sequences, take 11:   ")
    for (i in sequenceBoth.take(3+1+3+1+3)) {
        print("n=$i ")
    }
    println()
}
