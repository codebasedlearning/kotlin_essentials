// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

package unit0x01.exercises.proposed_solutions

private fun main() {
    println("\nProposed solution 'Rubies v3'\n--")
    solution()
}

open class FigureV2a(private var description: String) {
    open val area: Int
        get() = 0
    protected open val doc
        get() = "area:$area,description:'$description'"
    override fun toString() = "{${doc}}"
}

open class RectangleV2a(description: String, var width: Int, var length: Int)
    : FigureV2a(description) {
    override val area: Int
        get() = width * length
    override val doc
        get() = "${super.doc},width:$width,length:$length"
}

open class SquareV2a(description: String, width: Int)
    : RectangleV2a(description,width,width)

private fun solution() {
    val square = SquareV2a("sq v2a", 11)
    println("1 | $square")

    square.length = 12              // is this ok?
    println("2 | $square")          // see https://de.wikipedia.org/wiki/Liskovsches_Substitutionsprinzip
}
