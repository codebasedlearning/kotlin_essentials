// (C) 2025 A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x02.utils

enum class TerminalColor(val code:String) {
    Red("\u001B[31m"),
    Green("\u001B[32m"),
    Yellow("\u001B[33m"),
    Blue("\u001B[34m"),
    Magenta("\u001B[35m"),
    Cyan("\u001B[36m"),
    White("\u001B[37m"),
    Reset("\u001B[0m"),
    Empty("");
    override fun toString(): String = code
}

fun String.paintIn(color:TerminalColor) ="$color$this${TerminalColor.Reset}"
