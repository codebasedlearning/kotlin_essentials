// (C) A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x01.e_classes

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/*======================================================================================================================
Polymorphism Subtyping
======================================================================================================================*/

fun main() {
    println("Kotlin Essentials -> Classes | Polymorphism")

    introduceInheritance()
    introduceInterfaces()
    introduceAdHocObjects()
    introduceSAM()
    introduceDelegationPattern()
    showComposeLikeDelegates()
}

/*======================================================================================================================
[Inheritance]

Understanding single inheritance.
  - The class you want to inherit from must be 'open'.
  - The same applies to the function you want to override.
  - Default is not open (final).
  - An 'override' member is open itself; use 'final override' to stop further overriding.
  - Implicit base class is 'Any' (provides 'equals', 'hashCode' and 'toString').
  - You can override a 'val' property with a 'var' property.
  - Base class initialization is done before derived class initialization.
  Ref.:
  - https://kotlinlang.org/docs/inheritance.html
======================================================================================================================*/

open class Base(val n: Int) {                                       // open means: can be inherited from
    open fun f() = println(" a| . Base::f, n=${this.n}")            // open means: can be overridden
    open var x = 1                                                  // properties can be overridden as well
}

class Derived(n: Int) : Base(n) {                                   // inherits from 'Base' but is closed then
    override fun f() {
        super.f()
        println(" b| . Derived::f")
    }
    override var x = 2
    override fun toString() = "Derived, n=$n, x=$x"                 // override 'toString' from 'Any'
}

fun introduceInheritance() {
    println("\n[Inheritance]\n---")

    val b: Base = Derived(23)                                       // w.o. 'Base' it would be of type Derived
    b.f()                                                           // polymorphic behaviour

    if (b is Derived) {                                             // from here type is fixed -> smart cast
        println(" 1| b=$b")
    }
}

/*======================================================================================================================
[Interfaces]

Understanding implementing interfaces.
  - Interfaces can contain declarations of abstract methods, as well as method implementations.
  - A property declared in an interface can either be abstract or provide implementations for accessors
    but can't have backing fields.
  - Interfaces can derive from other interfaces.
  Ref.:
  - https://kotlinlang.org/docs/interfaces.html
======================================================================================================================*/

interface Drivable {
    val speed: Double                                               // abstract, implicitly 'open'

    fun drive() = println(" a| . Drivable::drive")                  // not abstract, can also be implemented
    fun park()                                                      // abstract function must be implemented
}

class Car : Drivable {
    override val speed: Double = 0.0
    override fun drive() = println(" b| . Car::drive")
    override fun park() = println(" c| . Car::park")
}

fun introduceInterfaces() {
    println("\n[Interfaces]\n---")

    val car = Car()
    car.drive()
    car.park()

}

/*======================================================================================================================
[Ad-hoc Objects]

  Ref.:
  - https://kotlinlang.org/docs/object-declarations.html
======================================================================================================================*/
fun introduceAdHocObjects() {
    println("\n[Ad-hoc Objects]\n---")

    val adHoc = object {
        var x = 1
        var y = 2
    }
    println(" 1| 1+2=${adHoc.x + adHoc.y}")

    val obj = object : Drivable {
        override val speed: Double
            get() = TODO("Not yet implemented")

        override fun park() {
            TODO("Not yet implemented")
        }
    }
}

/*======================================================================================================================
[SAM] Single Abstract Methods.

  - The main difference is that a fun interface is a functional interface (SAM interface),
    meaning it has exactly one abstract method and supports SAM conversion: you can implement
    it directly using a lambda.
  Ref.:
  - https://kotlinlang.org/docs/fun-interfaces.html#sam-conversions
======================================================================================================================*/

fun interface IntPredicate {
    fun check(i: Int): Boolean
}

typealias IntPredicateType = (i: Int) -> Boolean

interface ClickHandler {
    fun onClick()
}

fun introduceSAM() {
    println("\n[SAM - Single Abstract Methods]\n---")

    val isEven1 = object : IntPredicate {       // see also ClickHandler below
        override fun check(i: Int): Boolean {
            return i % 2 == 0
        }
    }

    val isEven2 = IntPredicate { it % 2 == 0 }
    val isEven3: IntPredicateType = { it % 2 == 0 }

    println(" 1| b=${isEven1.check(7)}, b=${isEven2.check(7)}, b=${isEven3(7)}")

    // cf. Java SAM (Single Abstract Method)
    val ch = object : ClickHandler {
        override fun onClick() {
            println(" 2| onClick()")
        }
    }
    ch.onClick()

    // does not work for a regular Kotlin interface - by design; declare it as 'fun interface' (Kotlin 1.4+),
    // like 'IntPredicate' above, then it works
    // val d = ClickHandler { println("Kotlin SAM") }
    // for Java interfaces SAM conversion works out of the box (example Runnable)
    // interface Runnable {
    //   void run();
    // }

    val rJ = Runnable { println(" 3| Java SAM") }
    rJ.run()

    // because this is Kotlin... often a plain function type is all you need (no interface at all)
    val rK: () -> Unit = { println(" 4| Kotlin function type") }
    rK()
}

/*======================================================================================================================
[Delegation Pattern]

  Ref.:
  - https://kotlinlang.org/docs/delegation.html
======================================================================================================================*/

interface CanFly {
    fun fly()
}

interface CanSwim {
    fun swim()
}

class Plane : CanFly {
    override fun fly() = println(" a| Flying")
}

class Boat : CanSwim {
    override fun swim() = println(" b| Swimming")
}

class AirBoat :
    CanFly by Plane(),          // 'by' delegates as if we have multi-inheritance
    CanSwim by Boat() {

    // lazy properties: the value gets computed only upon first access;
    // 'by' delegates property access, and 'lazy' provides the delegate object;
    // since isFlying is a val, only reading is delegated;
    // the value is computed once and cached.
    val isFlying: Boolean by lazy {
        true
    }

    // there are also observable properties with listeners
    var altitude: Int by Delegates.observable(0) { _, old, new ->
        println(" c| . altitude changed $old -> $new")
    }
}

fun introduceDelegationPattern() {
    println("\n[Delegation Pattern]\n---")

    val ab = AirBoat()
    ab.fly()
    ab.swim()
    println(" 1| lazy ${ab.isFlying}")
    ab.altitude = 100
}

// minimal observable state holder
interface MutableStateInterface<T> {
    var value: T
    // fun addListener(listener: (T) -> Unit)       // just the idea of an observer
}

// simple implementation
class MyMutableState<T>(initial: T) : MutableStateInterface<T> {
    // private val listeners = mutableListOf<(T) -> Unit>()

    override var value: T = initial
        set(newValue) {
            field = newValue
            // listeners.forEach { it(newValue) }           // notify all observers
            println(" a|   State changed -> $newValue")     // or print instead
        }

    // override fun addListener(listener: (T) -> Unit) { listeners += listener }
}

// a property delegate so we can write `var count by StateDelegate(...)`
// (alternatively implement the interface 'ReadWriteProperty<Any?, T>')
class StateDelegate<T>(private val state: MutableStateInterface<T>) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = state.value
    operator fun setValue(thisRef: Any?, property: KProperty<*>, newValue: T) { state.value = newValue }
}

// a helper function
fun <T> myMutableStateOf(initial: T): StateDelegate<T> = StateDelegate(MyMutableState(initial))

// mimics 'var count by mutableStateOf(0)' from Jetpack Compose (Android and Compose Multiplatform)
fun showComposeLikeDelegates() {
    println("\n[Compose-like State Delegates]\n---")

    val state = MyMutableState(1)

    println(" 1| Initial state = ${state.value}")
    state.value++
    state.value += 5
    println(" 2| Final state = ${state.value}\n")

    var counter by myMutableStateOf(11)

    // simulate UI using the variable directly
    println(" 3| Initial counter = $counter")
    counter++
    counter += 5
    println(" 4| Final counter = $counter")
}