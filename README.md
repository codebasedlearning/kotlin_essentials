# Kotlin Crash Course for experienced Programmers

## The Idea

First, a word about the target audience.

> This crash course is intended for students or interested people
who *already have some programming experience*, know another object-oriented language (Java, C++, C#, Python, etc...)
and want to know how to implement a task in Kotlin.

This means that we will not explain what a class means or what a loop is good for - that is assumed to be known.
Rather, the focus is on Kotlin-specific features and language constructs. Of course, we will explain
what kind of tasks they solve or support.

## The Course

The course is divided into units, most consisting of an 'Essentials' and a 'More' part.

> The basic idea is to cover the most important and practical topics of Kotlin at the 'Essential' level.
At the 'More' level, there are more use cases and examples, but you can figure out
the gist of the snippet for yourself at home.

(To my students: The 'more' part is still part of the curriculum, which means
you should definitely look at the content.)

## Kotlin Sources

Almost all the topics discussed here can be found in the main reference https://kotlinlang.org/docs/home.html

## Video Material

Over time, I will be posting videos of the units on the 'codebasedlearning' YouTube channel, as well as providing
exercises with suggested solutions. These two projects will grow over time.

## Kotlin Std-Lib

Before we can run any Kotlin code, we need to configure the runtime environment, i.e. the Kotlin Standard Lib.
- In context menu on (root) 'essentials' choose 'Open Module Settings', then
    - on 'Project' (left row) choose some SDK or download any, e.g. 'openjdk-23' with 'Language level' 'SDK default';
    - on 'Module' (left row) and 'essentials' (middle row)
        - and 'sources' select 'Sources' for folder 'src',
        - and 'dependencies' add plus (below) 'Library...', 'from Maven'
          and search for Kotlin version, e.g. 'org.jetbrains.kotlin:kotlin-stdlib:2.0.20' and confirm the rest.
- Then, inside 'src', 'Run' should find 'main' and work.

## Comments

Please feel free to send constructive comments and additions to [me](mailto:info@codebasedlearning.dev).
