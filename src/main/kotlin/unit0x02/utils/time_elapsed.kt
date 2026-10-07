// (C) A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x02.utils

import kotlin.time.TimeSource

// simple formatted time, use:
//      val time = TimeSource.Monotonic.markNow()
//      println("1 | ${time.elapsed()} | ...")
fun TimeSource.Monotonic.ValueTimeMark.elapsed() = elapsedNow().inWholeMilliseconds.toString().padStart(4)
