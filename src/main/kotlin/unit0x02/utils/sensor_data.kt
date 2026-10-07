// (C) A.Voß, a.voss@fh-aachen.de, info@codebasedlearning.dev

package unit0x02.utils

// simulated sensors; for real asynchronous network I/O have a look at the Ktor client (https://ktor.io)

// 'random()' includes both bounds (Random.nextInt(from, until) would exclude the upper one)
fun readFromExternalSource(waitForResponse: Long, validRange:IntRange):Int
        = validRange.random().also{ Thread.sleep(waitForResponse) }

fun readTemperature():Int = readFromExternalSource(waitForResponse=100, validRange=20..25)
fun readWiFiRSSI():Int = readFromExternalSource(100, -50..-30)
fun readWiFiLatency():Int = readFromExternalSource(100, 5..20)
