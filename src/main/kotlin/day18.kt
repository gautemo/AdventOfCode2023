import shared.*

fun main() {
    val input = Input.day(18)
    println(day18A(input))
    println(day18B(input))
}

fun day18A(input: Input): Long {
    var on = Point(0, 0)
    val vectors = input.lines.map { line ->
        val (direction, meters) = line.split(' ')
        when(direction) {
            "R" -> Vector(on, Point(on.x + meters.toInt(), on.y))
            "D" -> Vector(on, Point(on.x, on.y + meters.toInt()))
            "L" -> Vector(on, Point(on.x - meters.toInt(), on.y))
            "U" -> Vector(on, Point(on.x, on.y - meters.toInt()))
            else -> throw Exception()
        }.also {
            on = it.b
        }
    }
    return lagoonSize(vectors)
}

fun day18B(input: Input): Long {
    var on = Point(0, 0)
    val vectors = input.lines.map { line ->
        val hexa = Regex("""\(#((\w|\d)+)\)""").find(line)!!.groupValues[1]
        val meters = hexa.dropLast(1).toInt(radix = 16)
        when(hexa.last()) {
            '0' -> Vector(on, Point(on.x + meters, on.y))
            '1' -> Vector(on, Point(on.x, on.y + meters))
            '2' -> Vector(on, Point(on.x - meters, on.y))
            '3' -> Vector(on, Point(on.x, on.y - meters))
            else -> throw Exception()
        }.also {
            on = it.b
        }
    }
    return lagoonSize(vectors)
}

private class Vector(val a: Point, val b: Point)

private fun lagoonSize(vectors: List<Vector>): Long {
    var sum = 0L
    for(y in vectors.minOf { minOf(it.a.y, it.b.y) } .. vectors.maxOf { maxOf(it.a.y, it.b.y) }) {
        val wallsForRow = vectors.filter {
            it.a.y != it.b.y && y >= minOf(it.a.y, it.b.y) && y <= maxOf(it.a.y, it.b.y)
        }.sortedBy { it.a.x }
        val digged = wallsForRow.zipWithNext { a, b ->
            val aDirUp = a.a.y > a.b.y
            val bDirUp = b.a.y > b.b.y
            val isHorizontallyConnected = vectors.any {
                it.a.y == y &&
                        ((it.a.x == a.a.x && it.b.x == b.a.x) ||
                                (it.b.x == a.a.x && it.a.x == b.a.x))
            }
            if((aDirUp && !bDirUp) || isHorizontallyConnected) {
                a.a.x to b.a.x
            } else {
                null
            }
        }.filterNotNull()
        sum += digged.sumOf {
            (it.second - it.first) + 1
        } - digged.count { a -> digged.any { b -> a.first == b.second } }
    }
    return sum
}
