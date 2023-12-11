import shared.Input
import shared.Point
import shared.XYMap
import kotlin.math.abs

/* WARNING messy code, not cleaned up yet */

fun main() {
    val input = Input.day(11)
    println(day11A(input))
    println(day11B(input))
}

fun day11A(input: Input): Long {
    val map = XYMap(input) { if(it == '#') it else null }
    return map.all().keys.foldIndexed(0) { index, acc, galaxy1 ->
        acc + map.all().keys.drop(index + 1).sumOf { galaxy2 ->
            map.pointDist(galaxy1, galaxy2)
        }
    }
}

fun day11B(input: Input, emptySize: Int = 1000000): Long {
    val map = XYMap(input) { if(it == '#') it else null }
    return map.all().keys.foldIndexed(0) { index, acc, galaxy1 ->
        acc + map.all().keys.drop(index + 1).sumOf { galaxy2 ->
            map.pointDist(galaxy1, galaxy2, emptySize)
        }
    }
}


private fun XYMap<Char>.pointDist(a: Point, b: Point, emptySize: Int = 2): Long {
    var count = 0L
    for(x in minOf(a.x, b.x) ..< maxOf(a.x, b.x)) {
        if(all().none { it.key.x == x }) {
            count += emptySize
        } else {
            count++
        }
    }
    for(y in minOf(a.y, b.y) ..< maxOf(a.y, b.y)) {
        if(all().none { it.key.y == y }) {
            count += emptySize
        } else {
            count++
        }
    }
    return count
}

fun expandUniverse(input: Input): String {
    val expanded = mutableListOf<String>()
    input.lines.forEach { line ->
        expanded += line
        if(line.all { it == '.' }) {
            expanded += line
        }
    }
    var addedXLines = 0
    for(x in 0 ..< input.lines[0].length) {
        if(input.lines.all { it[x] == '.' }) {
            for(i in expanded.indices) {
                expanded[i] = expanded[i].replaceRange(x + addedXLines, x + addedXLines, ".")
            }
            addedXLines++
        }
    }
    return expanded.joinToString("\n")
}