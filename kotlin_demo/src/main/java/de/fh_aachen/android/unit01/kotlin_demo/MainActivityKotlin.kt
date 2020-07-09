package de.fh_aachen.android.unit01.kotlin_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.reflect.full.primaryConstructor

//import kotlin.reflect.full.primaryConstructor

// an until-end-of-line comment: e.g. by A.Voss with help of kotlinlang.org

/*
 * also a comment, e.g.
 * see: https://kotlinlang.org/docs/reference/
 * and: https://kotlinlang.org/docs/tutorials/koans.html
 *
 * bzw: The name comes from Kotlin Island, near St. Petersburg (part of JetBrains sits there).
 *
 */

const val answer = 42   // constant value on top-level, cannot be modified
// Integer type is inferred

class MainActivityKotlin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            usingDeclarations(arrayOf())        // a function call with params
            usingControlFlowStatements()        // and without; look for corresponding 'fun usingControlFlowStatements()'
            usingClassesAndConstructors()
            usingClassesAndInheritance()
            usingClassesAndProperties()
            usingClassesAndInterfaces()
            usingClassesAndAnonymousObjects()
            usingClassesAndGenerics()
            usingClassesAndExtensions()
            usingCollections()
            usingFunctions()
            usingNullables()
            usingJava()
            usingYield()
            usingOpOverloading()
            usingReflection()
            usingScopingFunctions()
            usingFinalBlock()
            usingDelegationPattern()
        }
    }

/*
 * goal: understand 'val' and 'var', types, 'println' and string interpolation
 */
    fun usingDeclarations(args : Array<String>) {
        val lenArgs = args.size     // val => read-only variable, assigned once
        var flexLenArgs = lenArgs   // var => mutable variable
        ++flexLenArgs               // note: no ';' needed

        // string interpolation aka string templates
        println("lenArgs+1: ${lenArgs+1})")     // calculated value
        println("flexLenArgs: $flexLenArgs")    // single reference

        val h: String = "hello"     // type explicitly given (not necessary)
        val ind = 4
        println("uppercase: ${h.substring(0,ind).toUpperCase()+h.substring(ind)}")

        // more examples
        val argsEmpty: Boolean = args.isEmpty() // type optional again
        val text = if (argsEmpty) "yes" else "no"
        println("args empty? $text")

        val pi = 3.1415926      // Double
        val e =  2.7182818F     // Float
        val i = 23              // Int (32 bit)
        val l = 42L             // Long
        val ll = 1L + 3         // Long, inferred
        val u = 53.toShort()    // Short
        val b = 17.toByte()     // Byte

        val t = true            // Boolean

        val c = 'A'             // Char
        val s = "string"        // String

        println("pi=$pi, e=$e, i=$i, l=$l, ll=$ll, u=$u, b=$b, t=$t")
        println("#.## pi=${"%.2f".format(pi)}")
        println("c=$c, s='$s'")

        val a: IntArray = intArrayOf(1, 2, 3)   // Array<Int>
        println("a[0]=${a[0]}")

        println()
    }


/*
 * goal: understand classical 'if', 'when' (sort of switch), all sorts of loops e.g. for, do-while,...
 */

    fun isDevilLongVersion(i: Int): Boolean {
        return (i==6 || i==66 || i==666)
    }

    fun isDevil(i: Int) = (i==6 || i==66 || i==666)

    fun usingControlFlowStatements() {
        val a = 1
        val b = 2

        // 'if' can be an expression, not only a statement
        val max = if (a > b) {
            print("max(a)=")
            a
        } else {
            print("max(b)=")
            b
        }
        println("$max")

        val i = 3
        // 'when' can be an expression as well
        when(i) {
            in 1..5 -> println("in range 1..5")
            6 -> print("is 6")
            else -> print("<1 or >6")
        }

        // or if-cascade
        when {
            (i in 1..5) -> println("in range 1..5")
            isDevil(i) -> print("is Devil")
        }


        // classical for
        for (i in 1..3) {       // note: i shadowed
            println("i=$i")
        }

        val l: IntArray = intArrayOf(1, 1, 2, 3, 5)
        for (i in l) {          // note: i shadowed
            println("l[]=$i")
        }

        // while and do-while as expected
        var k=10
        var steps = 0
        while (k > 0) {
            --k
            ++steps
        }

        do {
            ++k
            ++steps
            val kOK = (k<10)
        } while (kOK)         // note: kOK is visible here
        println("k=$k, steps=$steps")

        // also break and continue work
        steps=0
        for (j in 1..5) {
            ++steps
            if (j==2)
                break
        }
        println("for break (1): steps=$steps")

        // for with labels
        steps=0
        loop@ for (j1 in 1..5) {
            for (j2 in 1..5) {
                ++steps
                if (j1 == 2 && j2 == 2)
                    break@loop
            }
        }
        println("for break (2): steps=$steps")

        // implicit labels
        print("seq:")
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@forEach     // local return, i.e. the lambda
            print(it)
        }
        println("\n")
    }


/*
 * goal: understand classes and constructors
 */

    class A {
        constructor(i: Int) {
            println("A::ctor $i")
        }
    }

    class B(val name: String) {                     // primary constructor
        init {
            println("B::init block 1, name:'$name'")
        }

        constructor(i: Int) : this(i.toString()) {  // secondary constructors, needs to call primary
            println("B::ctor $i")
        }

        init {
            println("B::init block 2, length:${name.length}")   // note: init blocks know name
        }
    }

    fun usingClassesAndConstructors() {
        val a = A(1)        // no 'new' to create instances
        val b1 = B("one")
        val b2 = B(2)

        println("a=$a, b1=$b1, b2=$b2\n")
    }


/*
 * goal: understand classes and inheritance
 */

    // some base class, open means: can be inherited from
    open class Base {
        open fun v() { println("Base::v") }     // here open means: can be overridden
        fun f() { }
    }

    // inherits from Base but is closed then
    class Derived() : Base() {
        override fun v() { println("Derived::v"); super.v() }
        fun g() { println("Derived::g") }
    }

    // inherits from Base and is still open, but not v, this is final
    open class AnotherDerived() : Base() {
        final override fun v() { println("AnotherDerived::v") }
    }

    fun usingClassesAndInheritance() {
        val b: Base = Derived()
        b.v()

        if (b is Derived) {     // from here type is given
            b.g()
        }

        println()
    }


/*
 * goal: understand classes and getters and setters, so-called properties
 */

    // inherits implicitly from 'Any' (not the same as Java object)
    class S(val t: String) {
        // a property
        // note: there are no pure data members,
        // only properties with implicit or explicit getters/setters
        var n = 42

        // a property with a so-called 'backing' field
        var text: String = t
            // get() = field  // if not given explicitly
            get() { println("getter '$field'"); return field }
            set(value) { println("setter '$field'"); field = value }

        lateinit var x: String

        init {
            println("init '$text'")
        }

        fun initialize() {
            x = "x"
        }

        override fun toString(): String { return "{'${this.text}',$n}" }

        // note: properties can be overridden as well
        // note: there are also private, public, protected, internal
    }

    fun usingClassesAndProperties() {
        val c = S("huhu")
        println("1) c.text = $c")
        c.text = "neu"
        println("2) c.text = ${c.text}")
        c.initialize()
        println("3) c.x = ${c.x}")
    }


/*
 * goal: understand classes and interfaces
 */

    interface Driveable {
        val speed: Double   // abstract, implicitly 'open'

        fun drive()         // abstract, can also be implemented
    }

    class Car : Driveable {
        override val speed: Double = 0.0
        override fun drive() {
            TODO("not implemented")     // throws 'not implemented'
        }
    }

    fun usingClassesAndInterfaces() {
        val car = Car()
        println("car=$car")

        try {
            println("1) call drive()")
            car.drive()
            println("2) drive() called")
        }
        catch (e: NotImplementedError) {
            println("3) error: $e")
        }
        finally {
            println("4) finally")
        }
        println("5) end\n")
        // note: try is also an expression
    }


/*
 * goal: understand classes and anonymous objects
 */

    interface ClickHandler {
        fun onClick()
    }

    fun usingClassesAndAnonymousObjects() {
        val adHoc = object {
            var x: Int = 1
            var y: Int = 2
        }
        println(adHoc.x + adHoc.y)

        // cf. Java SAM (Single Abstract Method)
        val c = object : ClickHandler {
            override fun onClick() {
                println("onClick()")
            }
        }
        c.onClick()

        // does not work for Kotlin
        // val d = ClickHandler { println("Kotlin SAM") }
        // and only for Java
        val r = Runnable { println("Kotlin SAM") }

        // because this is Kotlin...
        val s : (Int) -> Unit = { x -> println("Kotlin SAM $x") }
        s(23)
        println()
    }


/*
 * goal: understand classes and generics
 */

    // some sort of generic function; 'Any' means any type
    fun hasPrefix(x: Any) = when(x) {
        is String -> x.startsWith("abc")
        else -> false
    }

    // a generic class
    class Box<T>(t: T) {
        var value = t
    }

// in and out modifier are called variance annotation

//    // covariant, i.e. data is read from producer
//    class Source<out T>(val t:T) {
//        fun get(): T { return t }
//    }
//
//    // contravariant, i.e. data can only be consumed
//    class Dest<in T> {
//        fun put(t: T) { }
//    }

    fun usingClassesAndGenerics() {
        val s = "abcPrefix"
        println("'$s' hasPrefix: ${hasPrefix(s)}")

        val x = 42
        println("$x hasPrefix: ${hasPrefix(x)} ")

        val box1: Box<Int> = Box<Int>(1)
        val box2 = Box(2)
        println("box1:${box1.value}, box2:${box2.value}")

//        val src: Source<String> = Source("oops")
//        val anysrc: Source<Any> = src
//        println("anysrc '${anysrc.get()}'")
//
//        val dest = Dest<Number>()
//        dest.put(1.0) // 1.0 type Double, a subtype of Number
//        val doubleDest: Dest<Double> = dest
    }


/*
 * goal: understand classes and extensions
 */

    fun String.first2():String { return this.substring(0,2) }

    fun usingClassesAndExtensions() {
        val s = "abcdefgh"
        val f2 = s.first2()
        println("string extension: '$f2' \n")
    }


/*
 * goal: using collections
 */

    fun usingCollections() {
        val list = listOf(1,2,3)                // List<Int>
        // no list.add
        println("list size: ${list.size}")

        val numbers = mutableListOf("1", "2")   // MutableList<String>
        numbers.add("3")
        println("numbers size: ${numbers.size}")

        val set = setOf(1, 2, 3, 2)
        println("set size: ${set.size}")

        val map = mutableMapOf("one" to 1, "two" to 2)
        map.put("three", 3)
        map["one"] = 11
        println("map size: ${map.size} \n")
    }


/*
 * goal: using functions and parameters
 */

    fun twice(x: Int):Int = 2 * x

    // with defaults
    fun addall(a: Int = 1, b: Int, c:Int = 2):Int { return a+b+c }

    // no return type means 'Unit'
    fun nothingInReturn() {}

    fun usingFunctions() {
        println("5*2=${twice(5)}")
        println("?+5+1=${addall(c=1,b=5)}")     // named parameter
        println("nothing=${nothingInReturn()}")

        val f: (Int) -> Int = ::twice
        println("5*2=${f(5)}")

        val sum: (Int, Int) -> Int = { x, y -> x + y }                      // lambda
        fun applyOp(x: Int, y: Int, op: (Int, Int) -> Int): Int = op(x, y)  // as parameter
        val s = applyOp(2, 3, sum)  // apply
        println("s=$s\n")
    }


/*
 * goal: using nullables
 * see: https://en.wikipedia.org/wiki/Tony_Hoare#Apologies_and_retractions
 * and the The Billion Dollar Mistake...
 */

    fun usingNullables() {
        var a: String = "abc"
        // a = null             // not allowed!!
        println("a=$a")
        var b: String? = "abc"
        b = null                // ok
        println("b=$b")

        val la = a.length       // safe
        // val lb = b.length    // unsafe as b could be null
        val lb = b?.length      // length or null, what type?
        println("la=$la, lb=$lb")

        val l1: Int = if (b != null) b.length else -1
        // or
        val l2 = b?.length ?: -1    // Elvis Operator (Haartolle)
        println("l1=$l1, l2=$l2")

        // use with care...
        try {
            val l = b!!.length
        } catch (e: NullPointerException) {
            println("error: $e")
        }

        println()
    }

/*
 * goal: using java
 * use: import java.io.*
 */

    fun usingJava() {
        val list = ArrayList<String>()      // Java instance (?)
        list.add("Item")
        val size = list.size
        val item = list[0]
        println("size=$size, item=$item \n")
    }


/*
 * goal: using yield
 */

    fun usingYield() {
        fun fibonacci() = sequence {    // builds a 'Sequence' lazily yielding values one by one
            var pair = Pair(0, 1)
            while (true) {
                yield(pair.first)
                pair = Pair(pair.second, pair.first + pair.second)
            }
        }

        println("fibs ${fibonacci().take(10).toList()} \n")
    }


/*
 * goal: using operator overloading
 */

    // modulo-class
    data class Modulo(val x: Int, val b: Int) {

        operator fun plus(increment: Int): Modulo {
            return Modulo((x + increment)%b, b)
        }

        override fun toString(): String { return "$x" }
    }

    fun usingOpOverloading() {
        val m = Modulo(3,5)
        val n = m+4
        println("(3+4)%5=$n \n")
    }


/*
 * goal: using reflection and class objects
 */

    fun usingReflection() {
        val modClazz = Modulo::class
        println("Modulo.name='${modClazz.simpleName}'")

        val m = modClazz.primaryConstructor?.call(3,5)      // can be null
        val n = m!!+4                                       // !! means, can throw NPE
        println("(3+4)%5=$n \n")
    }


/*
 * goal: using map, filter and let (scoping function)
 * see: https://medium.com/@fatihcoskun/kotlin-scoping-functions-apply-vs-with-let-also-run-816e4efb75f5
 */

    class Person(var name: String, var age:Int) {
        fun rename(newName:String) { name = newName }
        fun incAge() { ++age }
        override fun toString(): String { return "{'$name',$age}" }
    }

    fun usingScopingFunctions() {

        // usually we need a variable to work with
        val msX = Person("Ms. Smith", 20)
        println("Ms. Smith: $msX")
        msX.rename("Ms. Doe")
        msX.incAge()
        println("M(r)s. Doe: $msX")
        // from here we do not need it any longer

        // or with 'let', scoping function
        Person("Ms. Smith", 20).let {
            println("Ms. Smith: $it")
            it.rename("Ms. Doe")
            it.incAge()
            println("Ms. Doe: $it")
        }

        // or with apply
        Person("Mr. Smith", 20).apply {
            println("Ms. Smith: $this")
            rename("Ms. Doe")
            this.incAge()
            println("Ms. Doe: $this")

            // activity-this is also available
            val activity = this@MainActivityKotlin
        }

        val numbers = mutableListOf("one", "two", "three", "four", "five")

        println( numbers.map { it.length }.filter { it > 3 } )
        numbers.map { it.length }.filter { it > 3 }.let { println(it) }

        println()
    }

    /*
     * goal: understand blocks as last parameter, how does it work
     */

    fun <T> Collection<T>.myForEach(n:Int=-1, block: (T)->Unit):Collection<T> {
        this.forEach { block(it) }
        return this
    }

    fun usingFinalBlock() {
        val list = listOf(1,2,3)
        // standard
        list.forEach {  println("V1: $it") }
        // regular call
        list.myForEach(23, { it:Int -> println("V2: $it") })
        // lambda out of call, use default int, 'it' as default name
        list.myForEach { println("V3: $it") }
    }


/*
 * goal: using delegation pattern
 */

    interface CanFly {
        fun fly()
    }

    interface CanSwim {
        fun swim()
    }

    class Plane : CanFly {
        override fun fly() = println("Flying")
    }

    class Boot : CanSwim {
        override fun swim() = println("Swimming")
    }

    class Aeroboat :
        CanFly by Plane(),          // 'by' delegates as if we have multi-inheritance
        CanSwim by Boot() {
    }

    fun usingDelegationPattern() {
        val ab = Aeroboat()
        ab.fly()
        ab.swim()
    }

}
