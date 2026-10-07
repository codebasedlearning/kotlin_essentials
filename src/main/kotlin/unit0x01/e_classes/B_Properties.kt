// (C) A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x01.e_classes
import java.time.LocalDate

/*======================================================================================================================
Classes are a huge topic. We will start by looking at classes, properties, and methods.
Interfaces, overloads and more follows. We will also look at some sort of "static" behaviour.
======================================================================================================================*/

fun main() {
    println("Kotlin Essentials -> Classes | Properties")

    introduceProperties()
}

/*======================================================================================================================
[Properties]

Defining and understanding properties.
  - In fact, properties in Kotlin classes can be declared either as mutable ('var'), or as read-only ('val').
    This means that there is no classical member variable in the sense of a pure data field.
    What Kotlin does have, however, are properties with what are called 'backing fields' for data in memory.
  - A property may have an initializer, a getter and a setter, see examples below.
  - There are certain circumstances in which 'backing fields' come into existence. A backing field is
    generated for a property if it uses the default implementation of at least one of the accessors,
    or if a custom accessor references it using the 'field' identifier.
  - Explicit backing fields (stable since Kotlin 2.4): the field may have a more specific type than the property,
    see 'B3'. Outside the class you see a read-only 'List', inside you work with the 'MutableList'.
    This replaces the classical pattern 'private val _names = mutableListOf<String>(); val names: List<String> get() = _names'.
  Ref.:
  - https://kotlinlang.org/docs/properties.html
  - https://kotlinlang.org/docs/properties.html#explicit-backing-fields
======================================================================================================================*/

class B1 {
    var n = 1                                                   // mutable property with implicit getter and setter
    val m = 2                                                   // read-only property with getter (val, no setter)

    var text: String = "abc"                                    // default getter, private setter (with back. field)
        // get
        private set

    var counter = 0
        set(value) {                                            // block body
            if (value >= 0)
                field = value                                   // cannot use counter = value, why?
        }

    val isEmpty: Boolean
        get() = this.counter == 0                               // expression body, no backing field is generated
}

class B2 {
    var fullName: String = "Smith"
        get() {
            println(" a| . getter, field:'$field'"); return "Prof. $field"
        }
        set(value) {
            println(" b| . setter, field:'$field', new:'$value'"); field = value
        }

    var age = 50
        // get
        private set

    val bornIn: Int
        get() = LocalDate.now().year - age

}

class B3 {
    val names: List<String>                                     // public type: read-only List
        field = mutableListOf()                                 // explicit backing field: a MutableList

    fun add(name: String) {
        names.add(name)                                         // inside the class 'names' is a MutableList
    }
}

fun introduceProperties() {
    println("\n[Properties]\n---")

    val b1 = B1()
    // b1.text = "def"
    b1.counter = -10
    println(" 1| b1.n=${b1.n}, b1.m=${b1.m}, b1.text='${b1.text}', b1.counter=${b1.counter}, b1.empty=${b1.isEmpty}")

    val b2 = B2()
    b2.fullName = "Dr. Smith"
    println(" 2| b2.fullName=${b2.fullName}, b2.age=${b2.age}, b2.bornIn=${b2.bornIn}")

    val b3 = B3()
    b3.add("Ada")
    b3.add("Grace")
    // b3.names.add("Linus")                                    // error, from outside it is a read-only List
    println(" 3| b3.names=${b3.names}")
}
