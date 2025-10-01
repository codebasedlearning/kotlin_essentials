// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

package unit0x01.exercises.proposed_solutions

private fun main() {
    println("\nProposed solution 'Rubies v1a'\n--")
    solution()
}

open class FigureV1b(private var description: String) {
    open val area: Int
        get() = 0
    protected open val doc
        get() = "area:$area,description:'$description'"
    override fun toString() = "{${doc}}"
}

open class RectangleV1b(description: String, var width: Int, var length: Int)
    : FigureV1b(description) {
    override val area: Int
        get() = width * length
    override val doc
        get() = "${super.doc},width:$width,length:$length"
}

private fun solution() {
    val rect = RectangleV1b("box v1b", 10, 12)
    println("1 | $rect")
    rect.width = 100
    println("2 | $rect")
}
