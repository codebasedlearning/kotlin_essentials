// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

package unit0x01.exercises

private fun main() {
    println("\nSolution 'Shinies'\n--")
    solution()
}

private fun solution() {
    println("0 | join ${listOf(1,2,3).joinToString(",") {it.toString()}}\n")

//    listOf<Int>().compress().also {
//        println("1 | $it")
//        require(it=="[]")
//    }
//    listOf(1).compress().also {
//        println("2 | $it")
//        require(it=="[1]")
//    }
//    listOf(1,2,3).compress().also {
//        println("3 | $it")
//        require(it=="[1,2,3]")
//    }
//    listOf(1,2,3).compress { "'$it'"}.also {
//        println("4 | $it")
//        require(it=="['1','2','3']")
//    }
//
//    listOf(1.1,2.2,3.3).compress().also {
//        println("5 | $it")
//        require(it=="[1.1,2.2,3.3]")
//    }
}
