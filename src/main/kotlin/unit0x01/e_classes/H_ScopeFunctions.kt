// (C) 2025 A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x01.e_classes

/*======================================================================================================================
From here it gets interesting...
======================================================================================================================*/

fun main() {
    println("Kotlin Essentials -> Classes | Scope Functions")

    introduceScopeFunctions()
}

/*======================================================================================================================
[Scope Functions]

  - let, run, also, apply and with
  - Basically, these functions all perform the same action: execute a block of code on an object.
  - What's different is how this object becomes available inside the block and what the result
    of the whole expression is.
  Ref.:
  - https://kotlinlang.org/docs/scope-functions.html
======================================================================================================================*/

class Person(var name: String, var age: Int) {                      // simple Person item
    fun register() {}                                               // stub op.
    override fun toString() = "{'$name',$age}"
}

inline fun <T, R> T.doit(block: (T) -> R) {                         // like 'let' w.o. return
    block(this)
}

inline fun <T> T.dothis(block: T.() -> Unit): T {                   // like 'apply'
    block()
    return this
}

fun introduceScopeFunctions() {
    println("\n[Scope Functions]\n---")

    val person = Person("Mr. Smith", 20)
    println(" 1| case 1: person:$person")
    person.name = "Mr. Doe"                                         // got married
    person.age = 21                                                 // correct wrong data
    person.register()                                               // register again
    println(" 2|         person:$person")

    // summary:
    //  - many operations with an object that we never use again
    //  - also need a name for it
    //  - tedious to state the object over and over again
    //  - even better would be functions like 'gotMarried(newName)' and 'correctAge(newAge)',
    //    but that's not the point here...

    // start with a 'universal' name ('it') for the Person instance
    // (generic extension function with trailing lambda)
    Person("Mr. Smith", 20).doit {
        println(" 3| case 2: person:$it")
        it.name = "Mr. Doe"
        it.age = 22
        it.register()
        println(" 4|         person:$it")
    }

    // same as a receiver and with 'this' as return value such one may chain calls
    // (generic receiver function with trailing lambda)
    Person("Mr. Smith", 20).dothis {
        println(" 5| case 3: person:$this")
        name = "Mr. Doe"
        age = 23
        println(" 6|         person:$this")
    }.register()

    // these functions are the most used in the whole Kotlin world... but there is
    // more to come...

    val name1 = person.let { it.name+"!" }                          // it, return block result
    val name2 = person.run { name+"!" }                             // this, return block result
    val ref1 = person.also { it.name = "!" }                        // it, return object ref1
    val ref2 = person.apply { name = "!" }                          // this, return object ref2
    val name3 = with(person) {                                      // like 'run', but no extension function
        name+"!"
    }
    println(" 7| name1:$name1, name2:$name2, person1:$ref1, person2:$ref2, name3:$name3")

    // more applications: create an instance to work with and modify it before in one step
    val mrSmith = Person("Mr. Smith", 20).apply {
        age = 23                                                    // do something
    }
    // more applications: member chaining
    class Order(val productId: String, val items: MutableList<String> = mutableListOf()) {
        fun addItem(item: String) = apply { items.add(item) }
        fun sumUp() = apply { } // ...
    }
    val order = Order("fruits0023")
        .addItem("apple")
        .addItem("banana")
        .sumUp()

    // more applications: do something with the result (see also Extension Functions)
    val numbers = mutableListOf("one", "two", "three", "four", "five")
    numbers.map { it.length }.filter { it > 3 }.let { println(" 8| $it") }
}
