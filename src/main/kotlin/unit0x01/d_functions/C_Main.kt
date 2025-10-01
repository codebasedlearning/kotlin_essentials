// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

package unit0x01.d_functions

/*======================================================================================================================
We look into main with parameters and how to build a jar file.
======================================================================================================================*/

/*======================================================================================================================
[Main]

Defining 'main' with (command line) parameters.
  - 'main' can be equipped with parameters from the command line
  - Arguments can be set inside IntelliJ via Run/Debug configuration if not started from terminal.
  Ref.:
  - https://www.jetbrains.com/help/idea/running-applications.html

Building a jar from the command line needs the Kotlin compiler to be installed. Then use
  > kotlinc Hello.kt -include-runtime -d Hello.jar

Building it from inside IntelliJ uses artifacts. You can define them in the 'Module Settings', 'Artifacts'
  - Add a new one with plus and 'JAR' and 'From modules with dependencies' and then choose your function
    and 'extract to target JAR', e.g. 'essentials.jar'.
  - If successful, you can run it from the 'Terminal' with
    > java -jar out/artifacts/essentials_jar/essentials.jar
  - You can edit your choices in 'MANIFEST.MF' in 'MATA-INF'.

To run 'main' with arguments inside IntelliJ, but without artefacts, use or create a 'configuration' and pass the
runtime arguments there.
======================================================================================================================*/

fun main(args: Array<String>) {
    println("Kotlin Essentials -> Functions | Main")

    println(" 1| args: ${args.joinToString(", ")}")
}
