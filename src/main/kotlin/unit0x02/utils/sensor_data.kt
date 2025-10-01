// (C) 2024 A.Voß, a.voss@fh-aachen.de, kotlin@codebasedlearning.dev

package unit0x02.utils

import kotlin.random.Random

fun readFromExternalSource(waitForResponse: Long, validRange:IntRange):Int
        = Random.nextInt(validRange.first,validRange.last).also{ Thread.sleep(waitForResponse) }

fun readTemperatur():Int = readFromExternalSource(waitForResponse=100, validRange=20..25)
fun readWiFiRSSI():Int = readFromExternalSource(100, -50..-30)
fun readWiFiLatency():Int = readFromExternalSource(100, 5..20)
