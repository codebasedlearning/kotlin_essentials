// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

package unit0x02.c_flows

/*======================================================================================================================
This snippet is about flows, a way to return multiple asynchronously calculated values.
Many of the use cases for Channels are now better served by Flow and SharedFlow, and
starting from kotlinx.coroutines version 1.5.0; Channel is marked as obsolete in favor of Flow.
======================================================================================================================*/

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import unit0x02.utils.ConcurrencyInfo
import unit0x02.utils.elapsed
import unit0x02.utils.readTemperatur
import kotlin.time.TimeSource

fun main() {
    println("Kotlin Essentials -> Flows | Flows")

    motivateFlows()
    introduceColdFlows()
    viewColdFlowWithSubscribers()
    viewHotFlowWithSubscribers()
    viewStateFlowWithObservers()
    viewFlowOperators()
}

/*======================================================================================================================
[Motivation]

Different ways of providing multiple calculated values.
  - In some sense a flow is a non-blocking and asynchronous version of a sequence.
  - This makes it a perfect choice when dealing with streams of data, especially from asynchronous data sources
    or data that can take a significant amount of time to compute.
  - 'flow' is a builder function and the block can suspend.
  - Values are emitted from the flow using an 'emit' function (cf. 'yield'), and
    collected using a 'collect' function.
  - Restarting or reusing a flow is no problem as it starts from the beginning.
  - Similar to a 'sequence' the flow generator function 'dataFromFlow' returns immediately.
  Ref.:
  - https://kotlinlang.org/docs/flow.html#representing-multiple-values
======================================================================================================================*/
fun motivateFlows() {
    println("\n[Motivation]\n---")

    // handling multiple values generated from an external source (simulated)
    // ----------------------------------------------------------------------

    // readTemperatur(): (simple) computation of a value (it takes some time)

    // process a single data point
    fun process(data: Int) { print(" $data") }

    // V1: compute multiple values all at once, use a collection for exchange
    // - memory consumption
    // - all values calculated in advance
    // - blocking
    fun dataFromCollection() = List(3) { readTemperatur() }   // creates all values; =listOf(...)

    print(" 1| dataFromCollection:  [")
    dataFromCollection().forEach { process(it) }
    println(" ]")

    // V2: compute numbers one by one using sequences
    // + little memory
    // - blocking
    fun dataFromSequence() = sequence { (0..2).forEach { yield(readTemperatur()) } }  // create one by one

    print(" 2| dataFromSequence:    [")
    dataFromSequence().forEach { process(it) }
    println(" ]")

    // (simple) async. computation
    // it behaves like: async { read }.await, but with less overhead
    suspend fun dataOfAsync(): Int = withContext(Dispatchers.IO) { readTemperatur() }

    // V3: compute all at once but in suspending function
    // - memory consumption of all values
    // + non-blocking
    suspend fun dataFromSuspendFun() = List(3) { dataOfAsync() }

    print(" 3| dataFromSuspendFun:  [")
    runBlocking {
        dataFromSuspendFun().forEach { process(it) }
    }
    println(" ]")

    // V4: compute one by one using flows (and flow builders), compare with sequence above
    // + little memory
    // + non-blocking
    fun dataFromFlow() = flow { (0..2).forEach { emit(dataOfAsync()) } }

    print(" 4| dataFromFlow:        [")
    runBlocking {
        dataFromFlow().collect { process(it) }
    }
    println(" ]")
}

/*======================================================================================================================
[Cold Flows]

There are hot and cold flows, we start with the frozen one.
  - Even if it is already mentioned in the comments. A Flow is an asynchronous data stream and
    it doesn't have its own state. If you want a flow with state, consider [StateFlow].
  - Values are generated upon collector's demand, it starts when you collect (or observe) it.
    This is what is meant by 'cold'.
======================================================================================================================*/
fun introduceColdFlows() = runBlocking {
    println("\n[Cold Flows]\n---")

    // A Flow is an asynchronous data stream; it doesn't have its own state.
    // Values are generated upon collector's demand, it starts when you collect (or observe) it.
    // This is what is meant by 'cold'.

    fun dataFromFlow() = flow {                                 // Flow<Int>; or short: (1..3).asFlow()
        println(" a| . flow is started")
        for (i in 1..3) {
            println(" b| . emit $i")
            emit(i)
        }
    }

    println(" 1| obtain flow from flow builder")
    val flow = dataFromFlow()

    println(" 2| got flow, nothing started so far")
    delay(100L)
    println(" 3| still no activity...")

    flow.collect {
        println(" c| . value: $it")
    }
    println(" 4| done")
}

/*======================================================================================================================
[Cold Flow with Subscribers]

Is it an exclusive source of data?
  - The important message here is that both subscribers see the same data, regardless of the of their start.
======================================================================================================================*/
fun viewColdFlowWithSubscribers() = runBlocking {
    println("\n[Cold Flow with Subscribers]\n---")

    val mark = ConcurrencyInfo()

    fun dataFromFlow() = flow {                                 // Flow<Int>; or short: (1..4).asFlow()
        println("${mark("a", currentCoroutineContext())} . flow is started")
        for (i in 1..4) {
            delay(200)
            println("${mark("b", currentCoroutineContext())} . emit $i")
            emit(i)
        }
    }

    println("${mark("1")} obtain flow from flow builder")
    val flow = dataFromFlow()

    println("${mark("2")} launch consumer 1")
    launch {
        flow.collect { println("${mark("c", currentCoroutineContext())} . consumer 1, value: $it") }
    }

    println("${mark("3")} wait for 0.5s")
    delay(500)

    println("${mark("4")} launch consumer 2 (same 'flow')")
    launch {
        flow.collect { println("${mark("d", currentCoroutineContext())} . consumer 2, value: $it") }
    }
    println("${mark("5")} wait for all consumers (runBlocking)")
}

/*======================================================================================================================
[Hot Flow with Subscribers]

  - 'shareIn' makes a flow hot. That means, that subscribers get the data from the moment they subscribe.
    Previous data is gone.
    However, you can control the amount of data a new subscriber sees from the past by setting the 'replay' parameter.
  - SharedFlow' is now the flow type.
  Ref.:
  - https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/share-in.html
  - https://medium.com/androiddevelopers/things-to-know-about-flows-sharein-and-statein-operators-20e6ccb2bc74

Difference Cold Flow vs. Hot Flow:
    The main reason that a collect operation on a hot flow (such as SharedFlow or StateFlow) does not stop
    when the flow is "done" contrasts with a cold flow is due to the differing nature of cold and hot flows:
  - Cold Flows:
    A cold flow starts emitting values only when it is collected.
    Once all values have been emitted and the flow completes, the collection stops.
    Each new collector triggers a new emission from the beginning of the flow.
  - Hot Flows (like SharedFlow and StateFlow):
    Hot flows emit values independently of whether there are active collectors.
    They do not have a natural termination unless the scope in which they are running is explicitly canceled.
    New subscribers can collect values from hot flows at any time and receive the latest emitted values based
    on the flow's configuration.
    Hot flows typically continue to be active and emit values indefinitely, waiting for new subscribers.
======================================================================================================================*/
fun viewHotFlowWithSubscribers() = runBlocking {
    println("\n[Hot Flow with Subscribers]\n---")

    val mark = ConcurrencyInfo()                                // code is nearly identical

    fun dataFromFlow() = flow {
        println("${mark("a", currentCoroutineContext())} . flow is started")
        for (i in 1..4) {
            delay(200)
            println("${mark("v", currentCoroutineContext())} . emit $i")
            emit(i)
        }
    }

    println("${mark("1")} obtain flow from flow builder")
    // shareIn launches a coroutine (in this scope) responsible to share data to subscribers;
    // started = .Lazily means to start the flow when the first subscriber starts collecting,
    // if we use .Eagerly the flow starts immediately (maybe some numbers are missed then);
    // flow is of type SharedFlow;
    // we could also use a specific scope = CoroutineScope(Dispatchers.Default), e.g. to cancel all collectors;
    val flow = dataFromFlow()
        .shareIn(scope = this, started = SharingStarted.Lazily, replay = 0) // use replay=n to get past n values

    println("${mark("2")} launch consumer 1")
    // we collect all jobs as we need to cancel them, or use a new scope and cancel this
    val job1 = launch {
        flow.collect { println("${mark("c", currentCoroutineContext())} . consumer 1, value: $it") }
    }

    println("${mark("3")} wait for 0.5s")
    delay(500)

    println("${mark("4")} launch consumer 2 (same 'flow')")
    val job2 = launch {
        flow.collect { println("${mark("d", currentCoroutineContext())} . consumer 2, value: $it") }
    }
    println("${mark("5")} wait for all consumers (runBlocking)")
    delay(500)

    job1.cancelAndJoin()
    job2.cancelAndJoin()
}

/*======================================================================================================================
[StateFlow with Observers]

  - Here we still have a stream of data but not in the sense of the previous flows.
    StateFlow implementation models the classical observer-subscriber (publish-subscribe) pattern.
  - Note, that MutableStateFlow implements the StateFlow interface.
  - In applications such as Android models, we usually hide the state as seen in the comment.
  Ref.:
  - https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
  - https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
  - https://developer.android.com/jetpack/compose/tutorial
  - https://developer.android.com/jetpack/compose/documentation
  - https://developer.android.com/jetpack/compose/kotlin
======================================================================================================================*/
fun viewStateFlowWithObservers() = runBlocking {
    println("\n[State Flow with Subscribers]\n---")

    val mark = ConcurrencyInfo()                                // code is nearly identical

    fun dataFromFlow() = flow {
        println("${mark("a", currentCoroutineContext())} . flow is started")
        for (i in 1..4) {
            delay(200)
            println("${mark("b", currentCoroutineContext())} . emit $i")
            emit(i)
        }
    }

    println("${mark("1")} obtain flow from flow builder")
    // stateIn launches a coroutine (in this scope) responsible to share data to subscribers, similar to shareFlow;
    // flow is of type StateFlow;
    // flow has always a (latest) value (or initialValue)
    val flow = dataFromFlow()
        .stateIn(scope = this, started = SharingStarted.Lazily, initialValue = -1) // no replay, all get the last value first

    println("${mark("2")} launch consumer 1")
    // we collect all jobs as we need to cancel them, or use a new scope and cancel this
    val job1 = launch {
        flow.collect { println("${mark("c", currentCoroutineContext())} . consumer 1, value: $it") }
    }

    println("${mark("3")} wait for 0.5s")
    delay(500)

    println("${mark("4")} launch consumer 2 (same 'flow')")
    val job2 = launch {
        flow.collect { println("${mark("d", currentCoroutineContext())} . consumer 2, value: $it") }
    }
    println("${mark("5")} current state: ${flow.value}")
    println("${mark("6")} wait for all consumers (runBlocking)")
    delay(500)
    println("${mark("7")} current state: ${flow.value}")

    job1.cancelAndJoin()
    job2.cancelAndJoin()

    /* or use StateFlow in this way

    scope.launch {
        // calc value...
        stateFlow.value = "new value 1"
        // calc value...
        stateFlow.value = "new value 2"
    }

    or 'convert' a MutableStateFlow to a StateFlow by

    private val _uiState = MutableStateFlow(1)
    val uiState: StateFlow<Int>
        get() = _uiState
     */
}

/*======================================================================================================================
[Flow Operators]

Transformation at your finger tips.
  - Here is a list of the most commonly used (terminal) operators:
    map, filter, collect, onEach, reduce, take, combine.
  Ref.:
  - https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/
======================================================================================================================*/
fun viewFlowOperators() = runBlocking {
    println("\n[Flow Operators]\n---")

    println(" 1| 1..3 -> map, transform, filter, take")
    (1..3).asFlow()
        .map { it*5 }                                           // 5, 10, 15
        .transform {                                            // 4,5,6, 9,10,11, ...
            emit(it-1)
            emit(it)
            emit(it+1)
        }
        .filter { it % 2 == 0 }                                 // 4,6, 10, 14,16
        .take(4)
        .collect { println(" a| . $it") }
}

/*
ChatGPT

Kotlin’s Flow is conceptually similar to Java’s Stream, but there are some important differences,
especially in terms of functionality and usage.

Key Similarities

	1.	Reactive Streams: Both Flow in Kotlin and Stream in Java can represent a sequence of asynchronous
	    or lazy-computed data, which is useful for reactive programming.
	2.	Functional Operations: Both provide functional operations, such as map, filter, reduce, etc.,
	    allowing for transformation and processing of data in a pipeline.

Key Differences

	1.	Asynchronous vs. Synchronous:
	    •	Java Stream: Java’s Stream API is synchronous, meaning operations are executed immediately and
	        sequentially by default. There’s also a ParallelStream, but it doesn’t natively support asynchronous
	        flows like Kotlin’s Flow.
	    •	Kotlin Flow: Flow is designed to work with Kotlin Coroutines and supports asynchronous data streams.
	        It can emit values asynchronously over time and be collected in a non-blocking, coroutine-friendly way.
	2.	Cold vs. Hot Nature:
	    •	Kotlin Flow: Flow in Kotlin is “cold” by default, meaning nothing happens until it’s collected.
	        Each new collector triggers the flow from the beginning, making it suitable for on-demand data streaming.
	    •	Java Stream: Java Stream is a one-time, cold stream that can only be consumed once and cannot
	        be re-collected. This makes Stream less suitable for continuous or dynamic data streaming.
	3.	Handling of Errors:
	    •	Java Stream: Java Streams typically require manual handling of checked exceptions and don’t integrate
	        easily with Java’s exception handling mechanisms.
	    •	Kotlin Flow: Flow has built-in support for exception handling through operators like catch and retry,
	        making it more robust for asynchronous operations and error handling within reactive pipelines.
	4.	Support for Concurrency:
	    •	Kotlin Flow: Supports operators like buffer, conflate, and collectLatest, which help manage concurrency
	        more efficiently by controlling the flow of data between coroutines.
	    •	Java Stream: Java Streams lack these concurrency operators and require manual handling if you want
	        to handle data flow in a non-sequential or concurrent manner.
 */
