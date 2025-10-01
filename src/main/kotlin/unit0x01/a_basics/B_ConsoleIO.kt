// (C) 2025 A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x01.a_basics
import kotlin.math.abs

/*======================================================================================================================
To see and control what is going on, we need to print formatted messages to the console and read some text from it.
======================================================================================================================*/

fun main() {
    println("\nKotlin Essentials -> Basics | ConsoleIO")

    introduceStringInterpolation()
    readTextFromConsole()
}

/*======================================================================================================================
[String Interpolation]

Build strings with embedded variables and expressions and print to the console.
  - 'print' or, with line separator, 'println', prints a given message to standard output stream.
  - Use $x for a simple name or ${expression} for more complex expressions.
  - '$...' is called a 'template expressions'.
  More:
  - Format output via '.format'.
  - Nested template expressions are valid.
  Ref.:
  - https://kotlinlang.org/docs/strings.html#string-templates
======================================================================================================================*/

fun introduceStringInterpolation() {
    println("\n[String Interpolation]\n---")

    val counter = 9                                             // preview: 'val' read-only Int-variable
    println(" 1| counter=$counter")

    val message = " 2| counter+1=${counter + 1}"                // read-only String-variable with template expr.
    print(message); println(" (correct)")                       // print message, then print '!' with new line

    println(" 3| abs(-11)=${abs(-11)}")                         // more complex output
    println(
        " 4| when-expression: ${when (counter) {                // even nested expressions can be used
            9 -> "nine($counter)"                               // preview: 'when' see 'control-flow'
            else -> "else"
        }
        }"
    )

    val pi = 3.1415926                                          // type Double
    println(" 5| pi=$pi, formatted: pi=${"%.2f".format(pi)}")   // specific format
}

/*======================================================================================================================
[Text Input]

Read string from standard input stream, usually the console.
  - 'readln' reads a line of input from the standard input stream and returns it.
  - Line terminator is not included in the returned string.
  - There are situations where 'readln' can throw a 'RuntimeException' - see 'More'.
  - The same goes for 'toInt'. It is usually necessary to enclose these in try-catch (we do this later).
  - Input can be skipped here to see all the essentials without having to enter text each time.
  More:
  - If you are reading from the console and the user is asked to enter text, how can this result in 'null'?
    'null' can be the result if the input stream is redirected to a file and the end of file has been reached.
  - 'readln' and 'readlnOrNull' use 'readLine' and are designed as convenience functions in the style of
    other library functions.
  Ref.:
  - https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/readln.html
======================================================================================================================*/

fun readTextFromConsole() {
    println("\n[Text Input]\n---")

    print(  " 1| input number (n <enter>): ")
    val line = readln()                                         // string without '\n', use readlnOrNull without exception
    println(" 2| as text: '$line'")
    println(" 3| as int: ${line.toInt()}")                      // use toIntOrNull without exception
}
