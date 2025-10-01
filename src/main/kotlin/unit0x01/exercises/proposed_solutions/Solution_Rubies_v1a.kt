// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

package unit0x01.exercises.proposed_solutions

private fun main() {
    println("\nProposed solution 'Rubies v1a'\n--")
    solution()
}

open class FigureV1a(private var description: String) {
    open val area: Int = 0
    protected open val doc = "area:$area,description:'$description'"
    override fun toString() = "{${doc}}"
}

open class RectangleV1a(description: String, var width: Int, var length: Int)
    : FigureV1a(description) {
    override val area: Int = width * length
    override val doc = "${super.doc},width:$width,length:$length"
}

private fun solution() {
    val rect = RectangleV1a("box v1a", 10, 12)
    println("1 | $rect")    // => {area:0,description:'box',width:10,length:12} ???
    rect.width = 100
    println("2 | $rect")
}
