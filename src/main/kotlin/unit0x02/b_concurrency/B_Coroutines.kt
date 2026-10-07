// (C) A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x02.b_concurrency

/*======================================================================================================================
This snippet introduces coroutines and suspending functions, and discusses many topics
related to synchronous and asynchronous programming.
======================================================================================================================*/

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import unit0x02.utils.*
import java.io.File
import java.io.IOException
import java.util.concurrent.Executors
import kotlin.coroutines.ContinuationInterceptor
import kotlin.time.TimeSource
import kotlin.time.measureTime

fun main() {
    println("Kotlin Essentials -> Concurrency | Coroutines")

    introduceCoroutines()
    understandDispatchers()
    introduceSuspendingFunctions()
    introduceJobs()
    introduceCoroutineScope()
    discussConcurrency()

    println("\n-- More --")
    compareToThreads()
    moreOnCoroutines()
    moreOnVirtualThreads()
    moreOnExceptionsAndGlobalScope()
    moreOnFileIO()
}


/*======================================================================================================================
[Launch and Wait]

Note the timestamps.
  - A coroutine is a generalization of a subroutine or function. Unlike subroutines, coroutines allow multiple
    entry points for suspending and resuming execution at certain locations (what we see later).
    It is some sort of lightweight thread.
  - 'runBlocking' is a coroutine builder, i.e. a function that can launch a new coroutine.
    Examples of coroutine builders include 'launch', 'async', 'runBlocking', and more.
  - This function is primarily used in 'main' and test functions to prevent program exit before all coroutines
    have completed their execution. It should not be used from a coroutine.
  - Understand the role of the Dispatchers. If none is given, a coroutine inherits the dispatcher of its parent;
    for 'runBlocking' this is an event loop on the calling thread, i.e. the 'main' thread here; see below.
  Ref.:
  - https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/run-blocking.html
  - https://kotlinlang.org/docs/coroutines-basics.html
  - https://www.kodeco.com/books/kotlin-coroutines-by-tutorials/v2.0/chapters/3-getting-started-with-coroutines
  - https://www.baeldung.com/kotlin/threads-coroutines
  - https://kotlinlang.org/docs/coroutines-overview.html#sample-projects

Dispatcher:
  - A Dispatcher determines what thread or threads the corresponding coroutine uses for its execution.
  - When using runBlocking without specifying a dispatcher, it inherits the dispatcher (and the context [Context])
    from the parent coroutine, or if there's no parent, it creates a new event loop in the current thread.
  - In most cases, you would use
    * Dispatchers.Default, optimized for CPU intensive work using a shared pool of threads,
    * Dispatchers.IO, optimized for network or disk/IO operations.
  - There are more expert options
    * Dispatchers.Main for UI work (Android, or on desktop via kotlinx-coroutines-swing/-javafx or Compose;
      without such a module it throws an exception), and
    * Dispatchers.Unconfined for specific and expert (!) use cases, it can lead to unexpected behaviors.
      (The coroutine starts in the caller thread, but only until the first suspension point.
       When the coroutine resumes, it resumes in the thread where the corresponding suspending function completes,
       which could be a different thread. Hence "Unconfined" – it's not confined to any specific thread.)
    * Custom dispatchers are also possible, when you require more fine-grained control over your coroutines' execution.
  - Dispatchers.Default and Dispatchers.IO share one underlying thread pool (hence the same thread names
    'DefaultDispatcher-worker-n'). IO just allows many more threads to block at the same time (64 by default),
    and switching from Default to IO often does not even change the thread.
    'Dispatchers.IO.limitedParallelism(n)' gives you a view with its own limit.
  Ref.:
  - https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html#unconfined-vs-confined-dispatcher
======================================================================================================================*/
fun introduceCoroutines() {
    println("\n[Introduce Coroutines]\n---")

    val mark = ConcurrencyInfo()

    println("${mark("1")} start coroutines 'Alan' and 'Beth'")
    runBlocking {
        launch(Dispatchers.Default) {   // coro Alan
            println("${mark("a", coroutineContext)} started (in '${Thread.currentThread().name}')") // or currentCoroutineContext()
            repeat(3) {
                // Thread.sleep(100L) would block, delay(100L) suspends
                delay(100L)
                println("${mark("b", coroutineContext)} - after sleep (in ${Thread.currentThread().name})")
            }
            println("${mark("c", coroutineContext)} end of coro")
        }
        launch(Dispatchers.Default) {   // coro Beth
            println("${mark("d", coroutineContext)} started (in '${Thread.currentThread().name}')")
            repeat(3) {
                delay(100L) // suspends
                println("${mark("e", coroutineContext)} - after sleep (in ${Thread.currentThread().name})")
            }
            println("${mark("f", coroutineContext)} end of coro")
        }

        println("${mark("2")} coros started, block two times for 125ms each")
        (1..2).forEach { i ->
            Thread.sleep(125L)
            println("${mark("3")} - step $i after sleep (in ${Thread.currentThread().name})")
        }
    }
    println("${mark("4")} all joined, end of main")
}

/*======================================================================================================================
[Understand Dispatchers]

When no Dispatcher is given, it inherits the context from the parent context, which is
'runBlocking' in this case.
Here, any blocking operation in one coroutine would block the main thread, impacting the
execution of the other coroutine and causing delays or freezes in the application.
Note, that 'Dispatchers.Main' needs a UI module (Android, Swing, JavaFX), otherwise it results in an error.

Dispatchers can be specified for launch and/or for runBlocking.
======================================================================================================================*/
fun understandDispatchers() {
    println("\n[Understand Dispatchers]\n---")
    val mark = ConcurrencyInfo()

    println("${mark("1")} before block 1")
    runBlocking {
        launch {
            println("${mark("a")} coroutine starts in ${threadName()}")
            Thread.sleep(100) // Blocks the main thread!
            //delay(100L)
            println("${mark("b")} coroutine ends in ${threadName()}")
        }
        launch {
            println("${mark("c")} coroutine starts in ${threadName()}")
            Thread.sleep(100) // Blocks the main thread!
            //delay(100L)
            println("${mark("d")} coroutine ends in ${threadName()}")
        }
    }

    println("${mark("2")} before block 2")
    runBlocking {
        launch(Dispatchers.Default) {
            println("${mark("e")} coroutine starts in ${threadName()}")
            Thread.sleep(100)
            println("${mark("f")} coroutine ends in ${threadName()}")
        }
        launch(Dispatchers.Default) {
            println("${mark("g")} coroutine starts in ${threadName()}")
            Thread.sleep(100)
            println("${mark("h")} coroutine ends in ${threadName()}")
        }
    }

    println("${mark("3")} before block 3")
    runBlocking(Dispatchers.Default) {
        launch {
            println("${mark("i")} coroutine starts in ${threadName()}")
            Thread.sleep(100)
            println("${mark("j")} coroutine ends in ${threadName()}")
        }
        launch {
            println("${mark("k")} coroutine starts in ${threadName()}")
            Thread.sleep(100)
            println("${mark("l")} coroutine ends in ${threadName()}")
        }
    }

    println("${mark("4")} after blocking")
}

/*======================================================================================================================
[Suspending Functions]

Make it explicit.
  - Suspending function: A function that can suspend the execution without blocking a thread.
    You can specify a function as suspending with the 'suspend' keyword.
  - Understand the idea of a suspension point.
  - delay() is a suspension point, or simply yield() (use it for cooperative behaviour).
======================================================================================================================*/
fun introduceSuspendingFunctions() {
    println("\n[Suspending Functions]\n---")
    val mark = ConcurrencyInfo()

    // this is a suspending function (keyword 'suspend')
    suspend fun doSomeWork400() {
        println("${mark("a", currentCoroutineContext())} coroutine started in ${threadName()}, work for 0.4s")
        delay(400L) // suspension point!
        println("${mark("b", currentCoroutineContext())} end coroutine")
    }
    // another suspending function with two suspension points
    suspend fun doSomeWork200() {
        println("${mark("c", currentCoroutineContext())} coroutine started in ${threadName()}, work for 0.2s")
        delay(200L) // suspension point!
        println("${mark("d", currentCoroutineContext())} in between in ${threadName()}, work for another 0.2s")
        delay(200L) // suspension point!
        println("${mark("e", currentCoroutineContext())} end coroutine")
    }

    println("${mark("1")} before blocking")
    runBlocking {
        // doSomeWork400() without launch is just a call: it suspends this coroutine until it is done (sequential),
        // but it does not block the thread
        println("${mark("2")} launch coroutine in ${threadName()}")
        // start new coroutines that execute concurrently but not parallel (when launched in 'main')
        launch { doSomeWork400() }
        launch { doSomeWork200() }
        println("${mark("3")} behind launch")
    }
    println("${mark("4")} after blocking")
}

/*======================================================================================================================
[Jobs]

You had one job.
  - 'launch' (among others) is an extension function on CoroutineScope.
    It returns a reference to the coroutine as a Job object.
  - You can join (wait for) a job, or you can cancel it. Cancellation only takes place
    at suspension points and is a crucial aspect of the cooperative nature of
    Kotlin coroutines.
Context
  - The coroutine context stores additional technical information used to run a given coroutine,
    like the coroutine custom name, or the dispatcher specifying the threads the coroutine should be scheduled on.
  - It is defined by its coroutine builder and the outer context (the context of the parent coroutine or,
    in the case of a top-level coroutine, the context of the runBlocking, launch, or async that created it).
  - The + operator is used to join context elements together, to create a combined context.
  - 'withContext' runs a block of code in a different context (e.g. another dispatcher), suspends until
    the block is done and returns its result - it is sequential, not concurrent. It is the standard way to move
    (long) blocking work elsewhere, e.g. 'withContext(Dispatchers.IO) { file.readText() }'.
    If something should run concurrently, use 'launch' or 'async'.
  - If you catch a 'CancellationException', rethrow it, otherwise cancellation may not propagate properly.
  - Cancellation in Kotlin Coroutines is cooperative, meaning that cancellation of a coroutine job
    is a request, not an order.
======================================================================================================================*/
fun introduceJobs() {
    println("\n[Jobs]\n---")
    val mark = ConcurrencyInfo()

    println("${mark("1")} before blocking")
    runBlocking {
        println("${mark("2")} launch 'job'")
        val job = launch {
            try {
                println("${mark("a", currentCoroutineContext())} coroutine started, work for 300ms")
                delay(300L)
                println("${mark("b", currentCoroutineContext())} coroutine ended")
            } catch (e: CancellationException) {
                println("${mark("c", currentCoroutineContext())} coroutine cancelled: '${e.message}'")
                throw e                                         // always rethrow, cancellation has to propagate
            }
        }
        println("${mark("3")} behind launch, work for 0.1s")
        delay(100L)
        println("${mark("4")} behind work, cancel 'job' or wait for it")
        // wait for job, or cancel (cancel() raises 'StandaloneCoroutine was cancelled')
        // job.join()
        job.cancel(cause = CancellationException("user request"))   // cancel is just a request, so
        job.join()                                              // wait until it is really done (or: cancelAndJoin())
        println("${mark("5")} 'job' done")

        // Dispatchers are only part of the truth - it is the context.
        // Join context elements together, here dispatcher and a name.
        val todo = launch(Dispatchers.Default + CoroutineName("todo-coro")) {
            val name = this.coroutineContext[CoroutineName]?.name
            val dispatcher = this.coroutineContext[ContinuationInterceptor]
            println("${mark("d", currentCoroutineContext())} 'todo' started in ${threadName()}, name: '${name}', dispatcher: '${dispatcher}'")
            // see above, suspends until it completes,
            withContext(Dispatchers.IO) {
                launch {
                    val dispatcher2 = this.coroutineContext[ContinuationInterceptor]
                    println("${mark("e", currentCoroutineContext())} next started in ${threadName()}, dispatcher: '${dispatcher2}'")
                }
            }
        }
        todo.join()
        println("${mark("6")} 'todo' done")
    }

    println("${mark("7")} after blocking")
}

/*======================================================================================================================
[Coroutine Scope]

Care for your children.
  - The coroutine scope is responsible for the structure and parent-child relationships between different coroutines.
    Hence, it implicitly defines the lifetime of a coroutine. It ensures that your coroutines are canceled
    when they're no longer needed and don't leak any resources.
  - New coroutines usually need to be started inside a scope.
  - When launch, async, or runBlocking are used to start a new coroutine, they automatically
    create the corresponding scope.
  - 'GlobalScope' is a global 'CoroutineScope' - see More.
  Ref.:
  - https://kotlinlang.org/docs/coroutines-and-channels.html#structured-concurrency
  - https://kotlinlang.org/docs/exception-handling.html#coroutineexceptionhandler
======================================================================================================================*/
fun introduceCoroutineScope() {
    println("\n[Coroutine Scope]\n---")
    val mark = ConcurrencyInfo()

    println("${mark("1")} before blocking")
    runBlocking {
        println("${mark("2")} coroutineScope start")
        try {
            // start new scope; if canceled, children are also canceled
            coroutineScope {
                launch {
                    println("${mark("a",currentCoroutineContext())} coroutine started in ${threadName()}, work for 300ms")
                    delay(300L)
                    println("${mark("b",currentCoroutineContext())} end coroutine")
                }
                println("${mark("c")} coro launched, work for 0.1s")
                delay(100L)
                println("${mark("d")} cancel coro")
                cancel()
                println("${mark("e")} coroutineScope end")
            }
        } catch (e: CancellationException) {
            println("${mark("f")} canceled")
        }
    }
    println("${mark("3")} after blocking")
}

/*======================================================================================================================
[Concurrency]

  Ref.:
  - https://kotlinlang.org/docs/shared-mutable-state-and-concurrency.html#mutual-exclusion
======================================================================================================================*/
fun discussConcurrency() {
    println("\n[Concurrency and Mutual Exclusion]\n---")

    // we always increment counter 1000 times
    val repeats = 1000
    var counter = 0

    // V1: What is the problem here?
    measureTime {                                               // returns a Duration (prints like '12.3ms')
        counter = 0
        runBlocking { repeat(repeats) {
            launch(Dispatchers.Default) { ++counter }
        } }
    }.let { println(" 1| count $counter, after $it") }

    // Mutual Exclusion: only one thread at a time can enter the critical region (withLock);
    // try with lock and unlock are also common.
    val mutex = Mutex()

    // V2: Does it work?
    measureTime {
        counter = 0
        runBlocking { repeat(repeats) {
            launch(Dispatchers.Default) { mutex.withLock { ++counter } }
        } }
    }.let { println(" 2| count $counter, after $it") }

    // V3: Maybe we should better use a scope, should we?
    measureTime {
        counter = 0
        runBlocking { repeat(repeats) {
            CoroutineScope(Dispatchers.Default).launch { mutex.withLock { ++counter } }
        } }
        // Thread.sleep(1000L)
    }.let { println(" 3| count $counter, after $it") }

    // V4: How can this be, counter>1000?
    measureTime {
        counter = 0
        runBlocking { repeat(repeats) {
            launch(Dispatchers.Default) { mutex.withLock { ++counter } }
        } }
    }.let { println(" 4| count $counter, after $it") }

    // V5: Another way to use an extra scope, OK?
    measureTime {
        counter = 0
        runBlocking { coroutineScope {
            repeat(repeats) {
                launch(Dispatchers.Default) { mutex.withLock { ++counter } }
            }
        }}
    }.let { println(" 5| count $counter, after $it") }
}

/*======================================================================================================================
Hints:
  V1:   counter is not protected and can be modified simultaneously, remember,
        ++counter means counter=counter+1 and if two threads read counter (right side) and update it, one update is lost
  V2:   Yes, it works. When you use runBlocking and launch a coroutine with Dispatchers.Default inside it, the
        coroutine inherits the context from runBlocking but uses the thread pool managed by Dispatchers.Default for
        its execution.
  V3:   Creating a new CoroutineScope with every coroutine launch can lead to a proliferation of
        independent coroutine scopes, which can be difficult to manage, especially regarding their lifecycle
        and cancellation. Each new CoroutineScope you create is not tied to any larger application lifecycle,
        which can potentially cause resource leaks if not handled properly.
  - Uncomment 'sleep'
  V4:   Some launched jobs are still active... calling 'sleep' before waits and so everything 'looks' fine.
  V5:   The CoroutineScope represents the lifecycle control boundary for coroutines. It allows you to launch
        new coroutines that inherit the context of the scope but primarily manages the overall coroutine lifecycle.
        The scope itself doesn't specify how and where the coroutine will run, but rather, it uses
        the CoroutineContext for that configuration. So, yes.
======================================================================================================================*/

/*======================================================================================================================
[Compare to Threads]

This function is structural comparable to the introduceThreads() function.
======================================================================================================================*/
fun compareToThreads() {
    println("\n[Compare to Threads]\n---")

    val mark = ConcurrencyInfo()

    println("${mark("1")} start coroutine 'Alan'")
    runBlocking {
        launch(Dispatchers.Default) {                           // Alan
            println("${mark("a",coroutineContext)} started (in '${Thread.currentThread().name}'), observe WiFi RSSI for 400ms")
            repeat(4) {
                // blocks running thread
                //      val rssi = readWiFiRSSI()
                // suspends, i.e. frees running thread, but creates a new coroutine what makes it less efficient
                //      val rssi = async(Dispatchers.IO) { readWiFiRSSI() }.await()
                // suspends, and handles it more efficient
                val rssi = withContext(Dispatchers.IO) { readWiFiRSSI() }
                println("${mark("b",coroutineContext)} - RSSI: ${rssi}dB (in ${Thread.currentThread().name})")
            }
            println("${mark("c",coroutineContext)} observation done, end of coroutine")
        }

        println("${mark("2")} observe temperature for 200ms")
        repeat(2) { readTemperature().let { println("${mark("3")} - temperature: ${it}°C") } }

        println("${mark("4")} observation done, create 'Beth'")

        launch(Dispatchers.Default) {                           // Beth
            println("${mark("d",coroutineContext)} started (in ${Thread.currentThread().name}), observe WiFi Latency for 300ms")
            repeat(3) {
                val latency = withContext(Dispatchers.IO) { readWiFiLatency() }
                println("${mark("e",coroutineContext)} - Latency: ${latency}ms (in ${Thread.currentThread().name})")
            }
            println("${mark("f",coroutineContext)} observation done, end of coroutine")
        }

        println("${mark("5")} wait for 'Alan' and 'Beth'")
    }
    println("${mark("6")} woke up, all joined, end of main")
}

/*======================================================================================================================
[More on Coroutines]

Impressive, do that with threads.
  - Unless you have CPU-bound tasks, creating and destroying, and context switching with coroutines
    uses much less memory and time. There is also less or no interaction with the operating system.
    This is why it is so fast compared to threads.
======================================================================================================================*/
fun moreOnCoroutines() {
    println("\n[More on Coroutines]\n---")

    // todo - use ConcurrencyInfo
    val time = TimeSource.Monotonic.markNow()

    println(" 1| ${time.elapsed()} | start 1000 coroutines and let them work for 0.1s")
    runBlocking {
        repeat(1000) { // launch a lot of coroutines
            launch {
                delay(100)
                print(".")
            }
        }
    }
    println("\n 2| ${time.elapsed()} | done")
}

/*======================================================================================================================
More on [Virtual Threads]

Since Java 21 the JVM has 'virtual threads' (Project Loom): lightweight threads managed by the JVM, not by the OS.
  - Like coroutines, you can have hundreds of thousands of them, and blocking calls (sleep, I/O) only park
    the virtual thread, not the underlying OS (carrier) thread.
  - Differences: virtual threads keep the classical blocking style without 'suspend', but have no structured
    concurrency, cancellation or Flow built in. And coroutines also run on Kotlin/JS, Native and Wasm.
  - Both can be combined: an executor for virtual threads can serve as a coroutine dispatcher, so blocking code
    inside coroutines does not exhaust a thread pool.
  Ref.:
  - https://docs.oracle.com/en/java/javase/21/core/virtual-threads.html
======================================================================================================================*/
fun moreOnVirtualThreads() {
    println("\n[More on Virtual Threads]\n---")

    val time = TimeSource.Monotonic.markNow()
    println(" 1| ${time.elapsed()} | start 10_000 virtual threads, each blocks for 0.1s")
    val threads = List(10_000) {
        Thread.ofVirtual().start { Thread.sleep(100) }          // a classical blocking call
    }
    threads.forEach { it.join() }
    println(" 2| ${time.elapsed()} | done")

    Executors.newVirtualThreadPerTaskExecutor().asCoroutineDispatcher().use { loom ->
        println(" 3| ${time.elapsed()} | start 10_000 coroutines on virtual threads, each blocks for 0.1s")
        runBlocking {
            repeat(10_000) {
                launch(loom) { Thread.sleep(100) }              // blocks, but only a virtual thread waits
            }
        }
        println(" 4| ${time.elapsed()} | done")
    }
}

/*======================================================================================================================
[More on Exceptions and GlobalScope]

  - 'GlobalScope' is a global 'CoroutineScope' not bound to any job. 'GlobalScope' is used to launch
    top-level coroutines which are operating on the whole application lifetime and are not cancelled prematurely.
  - Active coroutines launched in GlobalScope do not keep the process alive. They are like daemon threads.
  Ref.:
  - https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-global-scope/
======================================================================================================================*/
@OptIn(DelicateCoroutinesApi::class)
fun moreOnExceptionsAndGlobalScope() {
    println("\n[More on Exceptions and GlobalScope]\n---")

    // todo - use ConcurrencyInfo

    // define an exception handler for GlobalScope coroutines
    // note that they usually cannot be caught in try-catch, which is the usual approach
    val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler: got $exception, log it")
    }

    GlobalScope.launch(handler) {
        println(" 1| coroutine launched")
        throw RuntimeException("a serious error")
    }
    Thread.sleep(500L)
    println(" 2| end of program")
}

/*======================================================================================================================
[More on File IO]

Blocking I/O in a coroutine.
  - Move blocking calls to Dispatchers.IO with 'withContext' - no 'async { ... }.await()' needed, see 'compareToThreads'.
  - 'Result' is a type for 'success or failure'; 'fold' handles both cases.
  - Careful with 'runCatching' in coroutines: it also catches 'CancellationException', hence the explicit try-catch.
======================================================================================================================*/
fun moreOnFileIO() {
    println("\n[More on File IO]\n---")

    suspend fun loadFile(filePath: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val file = File(filePath)
            if (file.exists()) {
                Result.success(file.readText())
            } else {
                Result.failure(IOException("File not found: $filePath"))
            }
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

    runBlocking {
        for (path in listOf("build.gradle.kts", "/path/to/my/file.txt")) {
            loadFile(path).fold(
                onSuccess = { content -> println(" 1| '$path': ${content.lines().size} lines") },
                onFailure = { exception -> println(" 2| '$path': failed with '${exception.message}'") }
            )
        }
    }
}