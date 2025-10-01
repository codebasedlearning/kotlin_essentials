// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

package unit0x01.e_classes

/*======================================================================================================================
Classes are a huge topic. We will start by looking at classes, properties, and methods.
======================================================================================================================*/

fun main() {
    println("Kotlin Essentials -> Classes | Classes and Constructors")

    introduceClassesAndConstructors()

    println("\n-- More --")
    moreOnClassesAndConstructors()
}

/*======================================================================================================================
[Classes and Constructors]

Defining classes with constructors and member variables and methods.
  - First of all, your understanding of constructors, members and modifiers will remain the same
    as you know it from Java. However, in addition to the syntax, there are some other concepts
    that are different, and we will look at these here.
  - Always remember that Kotlin ends up being able to run on a JVM and is able to use or expose to the Java world.
    This explains some of the details.
  - The definition of the class as a whole can be much more dense. We will try to emphasise this by comparing
    the first classes, 'A1'to 'A4'. The main point of this section is the initialisation of an instance, see examples.
  - Member variables (or properties, as we'll see later) need to be initialised in one way or another.
    This is part of the 'avoid errors' strategy.
  - Apart from static elements, member functions are used in the usual way.
  - Default visibility is 'public'. 'private' and 'protected' work the same as in Java.
    'internal' is visible in the module, see 'viewValAndVar'.
  - No 'new' to create an instance.
  - You can also use Java types (if you need to), such as ArrayList<T>.
  Ref.:
  - https://kotlinlang.org/docs/classes.html
  - https://kotlinlang.org/docs/classes.html#secondary-constructors
======================================================================================================================*/

// a simple valid Kotlin class - but not Kotlin-like (compare to A2)
class A1 {
    val k = 12                                                  // init here (type inferred)
    val n: Int // = 13                                          //      or in constructor (type needed);
    // val m: Int                                               // error, need to be initialized somewhere
    // var l: Int                                               //      same is true for var

    constructor(n: Int) {                                       // constructor (ctor)
        this.n = n
        println(" a| . A1::ctor, n=${this.n}")
    }

    private val i1 = 0                                          // visible inside this class only
    protected val i2 = 0                                        // like private, but also visible in subclasses
    internal val i3 = 0                                         // visible inside this module
    public val i4 = 0                                           // any who sees the class sees its public members

    fun nTimes(m: Int) = this.n * m                             // a (public) member function as you know it
}

// Kotlin primary constructor and some members can be defined this way
class A2(val n: Int) {                                          // n is member
    init {                                                      // optional init blocks for more logic
        println(" b| . A2::init, n=${this.n}")
    }
    // [...]
}

// wild combination, only val ('k') and var ('l') are members, rest parameters ('m')
class A3(private val k: Int, public var l: Int, m: Int) {
    val n = k*m                                                 // init with 'k' and 'm' is ok
    init {
        println(" c| . A3::init, n=${this.n}, m=$m, k=${this.k}")   // access of parameter 'm' only from here
    }
}

// and it can be brief if you do not need more
class A4(val n: Int, private var m: Int = 0)

fun introduceClassesAndConstructors() {
    println("\n[Classes and Constructors]\n---")

    val a1 = A1(11)                                             // no 'new' to create instances
    println(" 1| a1.n=${a1.n}, a1.n*5=${a1.nTimes(5)}")
    val a2 = A2(12)
    println(" 2| a2.n=${a2.n}")
    val a3 = A3(k=3, l=4, m=2)
    println(" 3| a3.n=${a3.n}")
    val a4 = A4(n=1)
    println(" 4| a4.n=${a4.n}")
}


/*======================================================================================================================
More on [Classes and Constructors] - more constructors and inits
======================================================================================================================*/
class A5(val n: Int) {
    init {                                                      // first init block for more logic
        println(" a| . A5::init, n=${this.n}")
    }

    constructor(s: String) : this(s.toInt())                    // primary ctor must be called; no new member here

    init {                                                      // optional second init block
        println(" b| . A5::init")
    }
}

fun moreOnClassesAndConstructors() {
    println("\n[More on Classes and Constructors]\n---")

    val a5 = A5("22")
    println(" 1| a5.n=${a5.n}, a5=$a5")                         // no output/toString
}
