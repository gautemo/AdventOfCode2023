import shared.*
import kotlin.math.pow

/* WARNING not cleaned up yet */

fun main() {
    val input = Input.day(5)
    println(day5A(input))
    println(day5B(input))
}

fun day5A(input: Input): Long {
    val seeds = input.chunks[0].toLongs()
    val maps = input.chunks.drop(1).map { chunk ->
        MapConverter(
            chunk.lines().drop(1).map {
                val (destination, source, range) = it.toLongs()
                Converter(destination, source, range)
            }
        )
    }
    val mapped = seeds.map { seed ->
        var value = seed
        maps.forEach {
            value = it.map(value)
        }
        value
    }
    return mapped.min()
}

fun day5B(input: Input): Long {
    val seeds = input.chunks[0].toLongs().chunked(2)
    val maps = input.chunks.drop(1).map { chunk ->
        MapConverter(
            chunk.lines().drop(1).map {
                val (destination, source, range) = it.toLongs()
                Converter(destination, source, range)
            }
        )
    }
    var min = Long.MAX_VALUE
    seeds.forEach {
        var x = it[0]
        while (x < it[0] + it[1]) {
            var value = x
            maps.forEach {
                value = it.map(value)
            }
            min = minOf(min, value)
            x++
        }
    }
    return min
}

class MapConverter(val converters: List<Converter>) {
    fun map(from: Long): Long {
        return converters.firstOrNull { it.inRange(from) }?.map(from) ?: from
    }
}

class Converter(val destination: Long, val source: Long, val range: Long) {
    fun inRange(from: Long) = from >= source && from < source + range

    fun map(from: Long): Long? {
        if(from >= source && from < source + range) {
            return destination + from - source
        }
        return null
    }
}