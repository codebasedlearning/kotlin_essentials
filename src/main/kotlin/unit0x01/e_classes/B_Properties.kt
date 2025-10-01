// (C) 2025 A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

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
  Ref.:
  - https://kotlinlang.org/docs/properties.html
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

fun introduceProperties() {
    println("\n[Properties]\n---")

    val b1 = B1()
    // b1.text = "def"
    b1.counter = -10
    println(" 1| b1.n=${b1.n}, b1.m=${b1.m}, b1.text='${b1.text}', b1.counter=${b1.counter}, b1.empty=${b1.isEmpty}")

    val b2 = B2()
    b2.fullName = "Dr. Smith"
    println(" 2| b2.fullName=${b2.fullName}, b2.age=${b2.age}, b2.bornIn=${b2.bornIn}")
}
