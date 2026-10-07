// (C) A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x02.utils

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.CoroutineContext
import kotlin.time.TimeSource

data class ConcurrencyData(
    val name: String,
    val color: TerminalColor,
    val indent: Int,
    val indentStr: String = " ".repeat(indent),
    val start: TimeSource.Monotonic.ValueTimeMark = TimeSource.Monotonic.markNow())

class ConcurrencyInfo(private val valueNow: TimeSource.Monotonic.ValueTimeMark = TimeSource.Monotonic.markNow()) {
    companion object {
        private val niceNames = listOf(
            ConcurrencyData("Main",TerminalColor.Cyan,0),
            ConcurrencyData("Alan",TerminalColor.Red,11),
            ConcurrencyData("Beth",TerminalColor.Blue,22),
            ConcurrencyData("Carl",TerminalColor.Green,33),
            ConcurrencyData("Dana",TerminalColor.Cyan,44)
        )
    }
    private val nameToData = ConcurrentHashMap<String, ConcurrencyData>()
    private val currentNiceNameIndex = AtomicInteger(0) // an atomic counter to keep track of the next name

    private fun getOrStoreData(name: String) = nameToData.computeIfAbsent(name) {
        val index = currentNiceNameIndex.getAndUpdate { (it + 1) % niceNames.size }
        niceNames[index].copy(start = TimeSource.Monotonic.markNow())  // a copy, the shared templates stay untouched
    }

    // called by instance("1")
    // note: instead of passing the coroutine context explicitly, we could require it as a context parameter
    //       'context(scope: CoroutineScope)' (stable since Kotlin 2.4, see 'Context Parameters' in unit0x01)
    operator fun invoke(text: String, context:CoroutineContext?=null): String {
        val name = when {
            context != null -> context.hashCode().toString()
            //scope != null -> scope.coroutineContext.hashCode().toString()
            else -> Thread.currentThread().name
        }
        val saved = getOrStoreData(name)
        val marker = "${text.padStart(2)}|".paintIn(saved.color)
        val elapsed = "${valueNow.elapsed()}|"
        val indent = saved.indentStr
        val ms = saved.start.let { "${saved.name} ${it.elapsed()}|" }.paintIn(saved.color)
        return  "$marker $elapsed $indent$ms"
    }
}

fun threadName() = "'${Thread.currentThread().name}'"
