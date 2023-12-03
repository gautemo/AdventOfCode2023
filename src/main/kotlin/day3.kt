import shared.Input
import shared.Point
import shared.XYMap

fun main() {
    val input = Input.day(3)
    println(day3A(input))
    println(day3B(input))
}

fun day3A(input: Input): Int {
    val map = XYMap(input) { if (it == '.') null else it }
    val partNumbers = findPartNumbers(input)
    return partNumbers
        .filter { partNumber ->
            partNumber.points.any { p -> map.adjacents(p, true) { !it.isDigit() }.isNotEmpty() }
        }
        .sumOf { it.value }
}

fun day3B(input: Input): Int {
    val map = XYMap(input) { if (it == '.') null else it }
    val partNumbers = findPartNumbers(input)
    val gearPartPairs = map.all { it == '*' }.map { p ->
        val adjacents = map.adjacents(p.key, true).keys
        partNumbers.filter { it.points.intersect(adjacents).isNotEmpty() }
    }.filter { it.size == 2 }
    return gearPartPairs.sumOf { it[0].value * it[1].value }
}

private fun findPartNumbers(input: Input): List<PartNumber> {
    return input.lines.flatMapIndexed { y, line ->
        Regex("""\d+""").findAll(line).map { match ->
            PartNumber(match.value.toInt(), match.range.map { Point(it, y) })
        }
    }
}

class PartNumber(val value: Int, val points: List<Point>)