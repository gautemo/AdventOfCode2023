import shared.*

fun main() {
    val input = Input.day(20)
    println(day20A(input))
    println(day20B(input))
}

fun day20A(input: Input): Int {
    val modules = createModules(input)
    val broadcaster = modules.first { it is Broadcaster }
    repeat(1000) {
        broadcaster.pulse(false, "aptly")
        while (modules.any { it.buffer.isNotEmpty() }) {
            modules.forEach { it.triggerPulse() }
        }
    }
    return countHigh * countLow
}

fun day20B(input: Input): Long {
    val modules = createModules(input)
    val broadcaster = modules.first { it is Broadcaster }
    val conjuctionTalkingToOutput = modules.first {
        module -> module.connected.any { it is Output }
    } as Conjunction
    val requiredRemembers = conjuctionTalkingToOutput.remembers
        .map { it.key }
        .associateWith { null as  Long? }
        .toMutableMap()
    var count = 0L
    while (true) {
        broadcaster.pulse(false, "aptly")
        count++
        while (modules.any { it.buffer.isNotEmpty() }) {
            modules.forEach { it.triggerPulse() }
            conjuctionTalkingToOutput.remembers.forEach {
                if(it.value && requiredRemembers[it.key] == null) requiredRemembers[it.key] = count
            }
        }
        if(requiredRemembers.all { it.value != null }) {
            return requiredRemembers.toList().fold(1) { acc, pair ->
                lcm(acc, pair.second!!)
            }
        }
    }
}

var countHigh = 0
var countLow = 0

private sealed class Module(val name: String) {
    val buffer = mutableListOf<Pair<Boolean, String>>()

    var connected = emptyList<Module>()
        set(value) {
            field = value
            value.forEach {
                if(it is Conjunction) {
                    it.register(name)
                }
            }
        }

    fun pulse(high: Boolean, from: String) {
        if(high) countHigh++ else countLow++
        buffer.add(high to from)
    }

    fun triggerPulse() {
        val pulse = buffer.removeFirstOrNull()
        if(pulse != null) {
            when (this) {
                is FlipFlop -> {
                    if(!pulse.first){
                        on = !on
                        connected.forEach { it.pulse(on, name) }
                    }
                }
                is Conjunction -> {
                    remembers[pulse.second] = pulse.first
                    connected.forEach { c -> c.pulse(remembers.any { !it.value }, name) }
                }
                is Broadcaster -> {
                    connected.forEach { it.pulse(pulse.first, name) }
                }
                is Output -> {}
            }
        }
    }
}

private class FlipFlop(name: String) : Module(name) {
    var on = false
}

private class Conjunction(name: String) : Module(name) {
    var remembers = mutableMapOf<String, Boolean>()

    fun register(connectedFrom: String) {
        remembers[connectedFrom] = false
    }
}

private class Broadcaster(name: String) : Module(name)

private class Output(name: String): Module(name)

private fun createModules(input: Input): List<Module> {
    val output = Output("output")
    val modules = input.lines.map {
        val name = it.split(' ')[0].drop(1)
        when(it[0]) {
            'b' -> Broadcaster(name)
            '%' -> FlipFlop(name)
            '&' -> Conjunction(name)
            else -> throw Exception()
        }
    }
    input.lines.forEach { line ->
        val name = line.split(' ')[0].drop(1)
        val connected = line.split("->")[1].split(',').map(String::trim)
        modules.first { it.name == name }.connected = connected.map { c -> modules.find { it.name == c } ?: output }
    }
    return modules
}