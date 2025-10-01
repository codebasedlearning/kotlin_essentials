// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

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
import kotlin.coroutines.ContinuationInterceptor
import kotlin.system.measureTimeMillis
import kotlin.time.TimeSource
import kotlin.Result
import kotlin.coroutines.coroutineContext

fun main() {
    println("Kotlin Essentials -> Concurrency | Coroutines")

    introduceCoroutines()
    introduceSuspendingFunctions()
    introduceJobs()
    introduceCoroutineScope()
    discussConcurrency()

    println("\n-- More --")
    compareToThreads()
    moreOnCoroutines()
    moreOnExceptionsAndGlobalScope()
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
  - Understand the role of the Dispatchers. If not given, it launches from the given or default Dispatcher,
    what is 'main' here; see below.
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
    * Dispatchers.Main for UI work (not available in all environment but in Android), and
    * Dispatchers.Unconfined for specific and expert (!) use cases, it can lead to unexpected behaviors.
      (The coroutine starts in the caller thread, but only until the first suspension point.
       When the coroutine resumes, it resumes in the thread where the corresponding suspending function completes,
       which could be a different thread. Hence "Unconfined" – it's not confined to any specific thread.)
    * Custom dispatchers are also possible, when you require more fine-grained control over your coroutines' execution.
  - Thread names in both dispatchers Default and IO are similar due to the underlying framework creating these threads,
    but they are managed differently.
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
[Suspending Functions]

Make it explicit.
  - Suspending function: A function that can suspend the execution without blocking a thread.
    You can specify a function as suspending with the 'suspend' keyword.
  - Understand the idea of a suspension point.
  - delay() is a suspension point, or simply yield() (use it for cooperative behaviour).
======================================================================================================================*/
fun introduceSuspendingFunctions() {
    println("\n[Suspending Functions]\n---")
    //val time = TimeSource.Monotonic.markNow()
    val mark = ConcurrencyInfo()

    // this is a suspending function (keyword 'suspend')
    suspend fun doSomeWork400() {
        println("${mark("a", coroutineContext)} coroutine started in ${threadName()}, work for 0.4s")
        delay(400L) // suspension point!
        println("${mark("b", coroutineContext)} end coroutine")
    }
    // another suspending function with two suspension points
    suspend fun doSomeWork200() {
        println("${mark("c", coroutineContext)} coroutine started in ${threadName()}, work for 0.2s")
        delay(200L) // suspension point!
        println("${mark("d", coroutineContext)} in between in ${threadName()}, work for another 0.2s")
        delay(200L) // suspension point!
        println("${mark("e", coroutineContext)} end coroutine")
    }

    println("${mark("1")} before blocking")
    runBlocking {
        // doSomeWork400() without launch runs the function in the same thread, i.e. it blocks, like a call
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
  - The 'withContext' function is not designed to launch long-running operations, it is designed
    to perform a certain task in a different context (and return a result). For long-running operation,
    you should use 'launch' or 'async'.
  - Cancellation in Kotlin Coroutines is cooperative, meaning that cancellation of a coroutine job
    is a request, not an order.
======================================================================================================================*/
fun introduceJobs() {
    println("\n[Jobs]\n---")
    val time = TimeSource.Monotonic.markNow()

    println(" 1| ${time.elapsed()} | before blocking")
    runBlocking {
        println(" a| ${time.elapsed()} | . launch job")
        val job = launch {
            try {
                println(" A| ${time.elapsed()} | ... coroutine started")
                delay(200L)
                println(" B| ${time.elapsed()} | ... end coroutine")
            } catch (e: CancellationException) {
                println(" C| cancelled: '${e.message}'")
            }
        }
        println(" b| ${time.elapsed()} | . behind launch, work for 0.1s")
        delay(100L)
        println(" c| ${time.elapsed()} | . behind work, wait for job")
        // wait for job, or cancel (cancel() raises 'StandaloneCoroutine was cancelled')
        // job.join()
        job.cancel(cause = CancellationException("you are fired"))
        println(" d| ${time.elapsed()} | . job done")

        // Dispatchers are only part of the truth - it is the context.
        // Join context elements together, here dispatcher and a name.
        val todo = launch(Dispatchers.Default + CoroutineName("todo-coro")) {
            println(" D| ${time.elapsed()} | ... todo started in ${threadName()}, name: '${this.coroutineContext[CoroutineName.Key]?.name}', dispatcher: '${this.coroutineContext[ContinuationInterceptor]}'")
            withContext(Dispatchers.IO) {
                launch { println(" E| ${time.elapsed()} | ... next started in ${threadName()}, dispatcher: '${this.coroutineContext[ContinuationInterceptor]}'") }
            }
        }
        todo.join()
        println(" e| ${time.elapsed()} | . todo done")
    }

    println(" 2| ${time.elapsed()} | after blocking")
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
    val time = TimeSource.Monotonic.markNow()

    println(" 1| ${time.elapsed()} | before blocking")
    runBlocking {
        println(" a| ${time.elapsed()} | . coroutineScope start")
        try {
            // start new scope; if canceled, children are also canceled
            coroutineScope {
                launch {
                    println(" A| ${time.elapsed()} | ... coroutine started in ${threadName()}, work for 0.2s")
                    delay(200L)
                    println(" B| ${time.elapsed()} | ... end coroutine")    // never called
                }
                println(" b| ${time.elapsed()} | . coro launched, work for 0.1s")
                delay(100L)
                println(" c| ${time.elapsed()} | . cancel coro")
                cancel()
                println(" d| ${time.elapsed()} | . coroutineScope end")
            }
        } catch (e: CancellationException) {
            println(" e| ${time.elapsed()} | canceled")
        }
    }
    println(" 2| ${time.elapsed()} | after blocking")
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
    measureTimeMillis {
        counter = 0
        runBlocking { repeat(repeats) {
            launch(Dispatchers.Default) { ++counter }
        } }
    }.let { println(" 1| count $counter, after ${it}s") }

    // Mutual Exclusion: only one thread at a time can enter the critical region (withLock);
    // try with lock and unlock are also common.
    val mutex = Mutex()

    // V2: Does it work?
    measureTimeMillis {
        counter = 0
        runBlocking { repeat(repeats) {
            launch(Dispatchers.Default) { mutex.withLock { ++counter } }
        } }
    }.let { println(" 2| count $counter, after ${it}s") }

    // V3: Maybe we should better use a scope, should we?
    measureTimeMillis {
        counter = 0
        runBlocking { repeat(repeats) {
            CoroutineScope(Dispatchers.Default).launch { mutex.withLock { ++counter } }
        } }
        // Thread.sleep(1000L)
    }.let { println(" 3| count $counter, after ${it}s") }

    // V4: How can this be, counter>1000?
    measureTimeMillis {
        counter = 0
        runBlocking { repeat(repeats) {
            launch(Dispatchers.Default) { mutex.withLock { ++counter } }
        } }
    }.let { println(" 4| count $counter, after ${it}s") }

    // V5: Another way to use an extra scope, OK?
    measureTimeMillis {
        counter = 0
        runBlocking { coroutineScope {
            repeat(repeats) {
                launch(Dispatchers.Default) { mutex.withLock { ++counter } }
            }
        }}
    }.let { println(" 5| count $counter, after ${it}s") }
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
            println("${mark("c",coroutineContext)} observation done, end of thread")
        }

        println("${mark("2")} observe temperature for 200ms")
        repeat(2) { readTemperatur().let { println("${mark("3")} - temperature: ${it}°C") } }

        println("${mark("4")} observation done, create 'Beth'")

        launch(Dispatchers.Default) {                           // Beth
            println("${mark("d",coroutineContext)} started (in ${Thread.currentThread().name}), observe WiFi Latency for 300ms")
            repeat(3) {
                val latency = withContext(Dispatchers.IO) { readWiFiLatency() }
                println("${mark("e",coroutineContext)} - Latency: ${latency}ms (in ${Thread.currentThread().name})")
            }
            println("${mark("f",coroutineContext)} observation done, end of thread")
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
[More on Exceptions and GlobalScope]

  - 'GlobalScope' is a global 'CoroutineScope' not bound to any job. 'GlobalCcope' is used to launch
    top-level coroutines which are operating on the whole application lifetime and are not cancelled prematurely.
  - Active coroutines launched in GlobalScope do not keep the process alive. They are like daemon threads.
  Ref.:
  - https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-global-scope/
======================================================================================================================*/
@OptIn(DelicateCoroutinesApi::class)
fun moreOnExceptionsAndGlobalScope() {
    println("\n[More on Exceptions and GlobalScope]\n---")

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

fun moreOnFileIO() {

    suspend fun loadFile(filePath: String): Result<String> {
        return withContext(Dispatchers.IO) {
            async {
                val file = File(filePath)
                try {
                    if (file.exists()) {
                        Result.success(file.readText())
                    } else {
                        Result.failure(IOException("File not found: $filePath"))
                    }
                } catch (e: IOException) {
                    Result.failure(e)
                }
            }.await()
        }
    }

    runBlocking {
        launch {
            val result = loadFile("/path/to/my/file.txt")
            when {
                result.isSuccess -> {
                    val fileContent = result.getOrNull()
                    // Process the file content
                }

                result.isFailure -> {
                    val exception = result.exceptionOrNull()
                    // Handle the error, e.g., log or display an error message
                }
            }
        }
    }
}