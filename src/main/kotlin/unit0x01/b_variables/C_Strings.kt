// (C) A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x01.b_variables

import kotlin.io.encoding.Base64
import kotlin.uuid.Uuid

/*======================================================================================================================
This snippet is about strings.
======================================================================================================================*/

fun main() {
    println("\nKotlin Essentials -> Variables | Strings")

    introduceStrings()

    println("\n-- More --")
    moreOnStrings()
    moreOnStringUtilities()
}

/*======================================================================================================================
[Strings]

Working with strings.
  - Nothing special here, strings behave like Java strings. It is a reference type and an immutable
    sequence of characters. Despite being a reference type, String behaves similarly to primitive types,
    especially in terms of immutability. Once a String object is created, its content cannot be changed anymore.
  - Try to avoid string concatenation (s1+s2), use string interpolation instead.
  - For multiline strings, or strings with many quotes or backslashes, use 'raw strings', embedded in """.
    Escape sequences like '\n' do not work there, but templates ('$x') do.
  - To get rid of the leading whitespace, use 'trimIndent' or 'trimMargin'.
  More:
  - There are a lot of convenience functions for strings, most defined as extensions.
  - Multi-dollar interpolation (Kotlin 2.2): in $$"..." a single '$' is just a character and '$$x' is a template.
    Handy for JSON schemas, shell scripts, regex etc. - no more ${'$'} gymnastics.
  - Since Kotlin 2.2/2.4 the stdlib also has Base64, HexFormat and Uuid (all stable), see 'moreOnStringUtilities'.
  Ref.:
  - https://kotlinlang.org/docs/strings.html
  - Definitions in Strings.kt
======================================================================================================================*/

fun introduceStrings() {
    println("\n[Strings]\n---")

    val s1 = "hello"                                            // type String, implicitly typed
    val s2: String = "world"                                    // explicitly typed (not necessary, hence gray)
    println(" 1| s1='$s1', s2='$s2'")
    println(" 2| s1='$s1'\n" +
            "  | s2='$s2'")
    println(""" 3| raw strings \n " '' $ s1=$s1""")
    println("""
             4| s1='$s1'
             5| s2='$s2'
    """.replaceIndent(" "))
}

/*======================================================================================================================
More on [Strings]

Common string operations.
======================================================================================================================*/
fun moreOnStrings() {
    println("\n[More on Strings]\n---")

    val fileNameFromDB = "/path/folder/data.txt   "

    val fileName = fileNameFromDB.trim()                        // remove whitespace; more: trimStart, trimEnd
    println(" 1| fileName='$fileName'")

    val extensionStart = fileName.indexOf(".txt")               // index of substr or -1; more: lastIndexOf
    val isTxt = fileName.endsWith("txt")                        // endsWith; more: startsWith
    val containsTxt = fileName.contains("txt")                  // contains substr

    // preview: 'if'-expression
    // substring, isBlank (empty or whitespace)
    // more: substringBefore, substringAfter, isNotBlank, isEmpty, isNotEmpty
    val isBlank = (if (extensionStart!=-1) fileName.substring(extensionStart) else "").isBlank()

    // more: replace, replaceBefore, replaceAfter
    val outName = fileName.replaceFirst("txt","out")            // replace substr

    println(" 2| isTxt=$isTxt, containsTxt=$containsTxt, isBlank=$isBlank, outName='$outName'")

    val multiline = """
        1 - line 1
        2 - line 2
    """.trimIndent()
    val lines = multiline.lines()
    println(" 3| lines=$lines")

    val sentence = "This is a sentence."
    val words = sentence.split(" ")                             // splits into a (preview) 'List'
    println(" 4| words=$words")

    println(" 5| how to print a ${'$'}x in strings :-)")         // the classical way
    println($$" 6| how to print a $x in strings, with $$sentence") // multi-dollar interpolation (Kotlin 2.2)
    val schema = $$"""{ "$schema": "https://json-schema.org/draft/2020-12/schema", "title": "$${words[3]}" }"""
    println(" 7| $schema")
}

/*======================================================================================================================
More on [String Utilities]

Some newer stdlib helpers around strings and bytes.
  - Base64 and HexFormat are stable since Kotlin 2.2, Uuid since Kotlin 2.4.
  Ref.:
  - https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.io.encoding/-base64/
  - https://kotlinlang.org/docs/uuids.html
======================================================================================================================*/
fun moreOnStringUtilities() {
    println("\n[More on String Utilities]\n---")

    val encoded = Base64.encode("Kotlin".encodeToByteArray())  // String -> bytes -> Base64 text
    val decoded = Base64.decode(encoded).decodeToString()       // and back
    println(" 1| base64: '$encoded' -> '$decoded'")

    println(" 2| hex: 255 -> '${255.toHexString()}', 'Hi' -> '${"Hi".encodeToByteArray().toHexString()}'")

    val id = Uuid.random()                                      // random (version 4) UUID
    println(" 3| uuid: $id, parse(toString)==id: ${Uuid.parse(id.toString()) == id}")
}
