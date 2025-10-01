// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

package unit0x01.exercises.proposed_solutions

private fun main() {
    println("\nProposed solution 'Rubies v3'\n--")
    solution()
}

open class FigureV2b(private var description: String) {
    open val area: Int
        get() = 0
    protected open val doc
        get() = "area:$area,description:'$description'"
    override fun toString() = "{${doc}}"
}

open class RectangleV2b(description: String, var width: Int, var length: Int)
    : FigureV2b(description) {
    override val area: Int
        get() = width * length
    override val doc
        get() = "${super.doc},width:$width,length:$length"
}

open class SquareV2b(description: String, var width: Int)
    : FigureV2b(description) {
    override val area: Int
        get() = width * width
    override val doc
        get() = "${super.doc},width:$width"
}

private fun solution() {
    val square = SquareV2b("sq v2b", 11)
    println("1 | $square")

    // square.length = 12 // not allowed
}
