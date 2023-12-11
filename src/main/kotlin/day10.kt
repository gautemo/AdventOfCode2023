import shared.Input
import shared.Point
import shared.XYMap

fun main() {
    val input = Input.day(10)
    println(day10A(input))
    println(day10B(input))
}

fun day10A(input: Input): Int {
    val map = XYMap(input) { if (it == '.') null else it }
    val trail = getTrail(map)
    return trail.size / 2
}

/* next time, just be smart and count if number of pipes to the right is odd */
fun day10B(input: Input): Int {
    val map = XYMap(input) { if (it == '.') null else it }
    val trail = getTrail(map)
    val expandedMap = getExpandedEmptySpaces(trail, map)
    return getClosedPoints(expandedMap)
}

private fun getTrail(map: XYMap<Char>): List<Point> {
    val trail = mutableListOf(map.all { it == 'S' }.keys.first())
    while (true) {
        val on = trail.last()
        var next: Point? = null
        Point(on.x, on.y - 1).let { top ->
            if (
                listOf('S', '|', 'L', 'J').contains(map[on]) &&
                listOf('S', '|', '7', 'F').contains(map.getOrNull(top)) &&
                !trail.contains(top)
            ) {
                next = top
            }
        }
        Point(on.x + 1, on.y).let { right ->
            if (
                listOf('S', '-', 'L', 'F').contains(map[on]) &&
                listOf('S', '-', '7', 'J').contains(map.getOrNull(right)) &&
                !trail.contains(right)
            ) {
                next = right
            }
        }
        Point(on.x, on.y + 1).let { bottom ->
            if (
                listOf('S', '|', '7', 'F').contains(map[on]) &&
                listOf('S', '|', 'L', 'J').contains(map.getOrNull(bottom)) &&
                !trail.contains(bottom)
            ) {
                next = bottom
            }
        }
        Point(on.x - 1, on.y).let { left ->
            if (
                listOf('S', '-', '7', 'J').contains(map[on]) &&
                listOf('S', '-', 'L', 'F').contains(map.getOrNull(left)) &&
                !trail.contains(left)
            ) {
                next = left
            }
        }
        if (next == null) break
        next?.let { trail.add(it) }
    }
    return trail
}

private fun getExpandedEmptySpaces(trail: List<Point>, map: XYMap<Char>): XYMap<Char> {
    var expandedInput = ""
    for (y in 0..<map.height) {
        for (x in 0..<map.width) {
            if (trail.contains(Point(x, y))) {
                expandedInput += map[Point(x, y)]
                val connectsRight = getNeighbours(Point(x, y), trail).contains(Point(x + 1, y))
                expandedInput += if (connectsRight) {
                    "-"
                } else {
                    "e"
                }
            } else {
                expandedInput += ".e"
            }
        }
        expandedInput += '\n'
        for (x in 0..<map.width) {
            expandedInput += if (trail.contains(Point(x, y))) {
                val connectsBottom = getNeighbours(Point(x, y), trail).contains(Point(x, y + 1))
                if (connectsBottom) {
                    "|e"
                } else {
                    "ee"
                }
            } else {
                "ee"
            }
        }
        expandedInput += '\n'
    }
    return XYMap(Input(expandedInput.trim())) { if (it == '.' || it == 'e') it else null }
}

private fun getNeighbours(point: Point, list: List<Point>): List<Point> {
    val index = list.indexOf(point)
    if (index == 0) {
        return listOf(list.last(), list[1])
    }
    if (index == list.size - 1) {
        return listOf(list[index - 1], list[0])
    }
    return listOf(list[index - 1], list[index + 1])
}

fun getClosedPoints(map: XYMap<Char>): Int {
    val knownEscapes = mutableListOf<Point>()
    val knownTrapped = mutableListOf<Point>()

    fun checkEscape(point: Point): Boolean {
        val options = mutableSetOf(point)
        val visited = mutableListOf<Point>()
        while (options.isNotEmpty()) {
            val on = options.first()
            options.remove(on)
            visited.add(on)
            if (knownTrapped.contains(on)) break
            if (knownEscapes.contains(on) ||
                on.x == 0 ||
                on.y == 0 ||
                on.x == map.width - 1 ||
                on.y == map.height - 1
            ) {
                knownEscapes.addAll(visited + options)
                return true
            }
            options.addAll(map.adjacents(on).keys.filter { !visited.contains(it) })
        }
        knownTrapped.addAll(visited + options)
        return false
    }

    return map.all { it == '.' }.count { !checkEscape(it.key) }
}
