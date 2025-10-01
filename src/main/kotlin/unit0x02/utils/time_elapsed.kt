// (C) 2025 A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x02.utils

import kotlin.time.DurationUnit
import kotlin.time.TimeSource

// simple formatted time, use:
//      val time = TimeSource.Monotonic.markNow()
//      println("1 | ${time.elapsed()} | ...")
fun TimeSource.Monotonic.ValueTimeMark.elapsed() = elapsedNow().toLong(DurationUnit.MILLISECONDS).toString().padStart(4)
