// (C) 2025 A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x02.utils

import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.CoroutineContext
import kotlin.time.TimeSource

data class ConcurrencyData(
    val name: String,
    val color: TerminalColor,
    val intent: Int,
    val intentStr:String = " ".repeat(intent),
    var start: TimeSource.Monotonic.ValueTimeMark = TimeSource.Monotonic.markNow())

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
        niceNames[index].apply { this.start = TimeSource.Monotonic.markNow() }
    }

    // called by instance("1")
    // note: instead of calling instances it with () and providing the scope, we could make it a context receiver
    //       'context(CoroutineScope)', but this language feature is still experimental
    operator fun invoke(text: String, context:CoroutineContext?=null): String {
        val name = when {
            context != null -> context.hashCode().toString()
            //scope != null -> scope.coroutineContext.hashCode().toString()
            else -> Thread.currentThread().name
        }
        val saved = getOrStoreData(name)
        val marker = "${text.padStart(2)}|".paintIn(saved.color)
        val elapsed = "${valueNow.elapsed()}|"
        val intent = saved.intentStr
        val ms = saved.start.let { "${saved.name} ${it.elapsed()}|" }.paintIn(saved.color)
        return  "$marker $elapsed $intent$ms"
    }
}

fun threadName() = "'${Thread.currentThread().name}'"
