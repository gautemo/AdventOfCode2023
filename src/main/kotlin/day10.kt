import shared.Input
import shared.Point
import shared.XYMap

/* WARNING messy code, not cleaned up yet */

fun main() {
    val input = Input.day(10)
    println(day10A(input))
    println(day10B(input))
}

fun day10A(input: Input): Int {
    val map = XYMap(input) { if (it == '.') null else it }
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
        if(next == null) return trail.size / 2
        next?.let { trail.add(it) }
    }
}

fun day10B(input: Input): Int {
    val map = XYMap(input) { if (it == '.') null else it }
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
        if(next == null) break
        next?.let { trail.add(it) }
    }
    var expandedInput = ""
    for(y in 0 ..< map.height) {
        for(x in 0 ..< map.width) {
            if(trail.contains(Point(x, y))) {
                expandedInput += map[Point(x, y)]
                val connectsRight = getNeighbours(Point(x, y), trail).contains(Point(x + 1, y))
                expandedInput += if(connectsRight) {
                    "-"
                } else {
                    "e"
                }
            } else {
                expandedInput += ".e"
            }
        }
        expandedInput += '\n'
        for(x in 0 ..< map.width) {
            expandedInput += if(trail.contains(Point(x, y))) {
                val connectsBottom = getNeighbours(Point(x, y), trail).contains(Point(x, y + 1))
                if(connectsBottom) {
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
    println(expandedInput)
    val expandedMap = XYMap(Input(expandedInput.trim())) { if(it == '.' || it == 'e') it else null }
    var count = 0
    val knownEscapes = mutableSetOf<Point>()
    val knownTrapped = mutableSetOf<Point>()
    for(it in expandedMap.all()) {
        if(it.value == '.') {
            val answer = canEscape(it.key, expandedMap, knownEscapes.toList(), knownTrapped.toList())
            if(answer.second) {
                knownEscapes.addAll(answer.first)
            } else {
                knownTrapped.addAll(answer.first)
                count++
            }
        }
    }
    return count
}

private fun getNeighbours(point: Point, list: List<Point>): List<Point> {
    val index = list.indexOf(point)
    if(index == 0) {
        return listOf(list.last(), list[1])
    }
    if(index == list.size - 1) {
        return listOf(list[index - 1], list[0])
    }
    return listOf(list[index - 1], list[index + 1])
}

private fun canEscape(point: Point, map: XYMap<Char>, knownEscapes: List<Point>, knownTrapped: List<Point>): Pair<List<Point>, Boolean> {
    println("on y ${point.y} of ${map.height-1}")
    val visited = mutableListOf<Point>()
    val options = mutableSetOf(point)
    while (options.isNotEmpty()) {
        val on = options.first()
        if(knownEscapes.contains(on)) {
            return visited to true
        }
        if(knownTrapped.contains(on)) {
            return visited to false
        }
        if(on.x == 0 || on.y == 0 || on.x == map.width - 1 || on.y == map.height - 1) {
            return visited to true
        }
        options.addAll(map.adjacents(on).keys.filter { !visited.contains(it) })
        visited.add(on)
        options.remove(on)
    }
    return visited to false
}