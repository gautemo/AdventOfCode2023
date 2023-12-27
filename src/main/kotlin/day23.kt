import shared.*
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = Input.day(23)
    println(day23A(input))
    println(day23B(input))
}

fun day23A(input: Input): Int {
    val map = XYMap(input) { if (it == '#') null else it }
    val options = mutableListOf(listOf(Point(1, 0)))
    var longest = 0
    while (options.isNotEmpty()) {
        val next = options.removeFirst()
        if (next.last().y == map.height - 1) {
            longest = max(longest, next.size - 1)
        }
        map.adjacents(next.last())
            .filter { !next.contains(it.key) }
            .filter {
                when (it.value) {
                    '^' -> it.key.y < next.last().y
                    'v' -> it.key.y > next.last().y
                    '<' -> it.key.x < next.last().x
                    '>' -> it.key.x > next.last().x
                    else -> true
                }
            }.forEach {
                options.add(next + it.key)
            }
    }
    return longest
}

fun day23B(input: Input): Int {
    val map = XYMap(input) { if (it == '#') null else it }
    val graph = mutableListOf<Graph>()
    val meetingPoints = map.all().map { it.key }.filter {
        map.adjacents(it).size > 2
    }
    meetingPoints.forEachIndexed { index, a ->
        graph.addAll(
            meetingPoints
                .drop(index + 1)
                .filter { it != a }
                .mapNotNull { b ->
                    getDist(a, b, map)?.let {
                        Graph(a, b, it)
                    }
                }
        )
    }
    meetingPoints.firstNotNullOf { b ->
        getDist(Point(1, 0), b, map)?.let {
            Graph(Point(1, 0), b, it)
        }
    }.let { graph.add(it) }
    meetingPoints.firstNotNullOf { b ->
        getDist(Point(map.width - 2, map.height - 1), b, map)?.let {
            Graph(Point(map.width - 2, map.height - 1), b, it)
        }
    }.let { graph.add(it) }

    val options = mutableListOf(listOf(graph.first { it.a.y == 0 }))
    var longest = 0
    while (options.isNotEmpty()) {
        val next = options.removeFirst()
        val on = when {
            next.size == 1 -> next.last().b
            next.last().a == next[next.size - 2].a -> next.last().b
            next.last().a == next[next.size - 2].b -> next.last().b
            next.last().b == next[next.size - 2].a -> next.last().a
            next.last().b == next[next.size - 2].b -> next.last().a
            else -> throw Exception()
        }
        if (on.y == map.height - 1) {
            longest = max(longest, next.sumOf { it.dist })
        }
        graph
            .filter { it.a == on || it.b == on }
            .filter { g ->
                if(g.a == on) next.none { it.a == g.b || it.b == g.b }
                else next.none { it.a == g.a || it.b == g.a }
            }
            .forEach { options.add(next + it) }
    }
    return longest
}

private fun getDist(a: Point, b: Point, map: XYMap<Char>): Int? {
    val options = mutableListOf(listOf(a))
    while (options.isNotEmpty()) {
        val next = options.removeFirst()
        if(next.last() == b) {
            return next.size - 1
        }
        val nextOptions = map.adjacents(next.last())
            .filter { !next.contains(it.key) }
        if(nextOptions.size == 1 || next.last() == a) {
            nextOptions.forEach {
                options.add(next + it.key)
            }
        }
    }
    return null
}


class Graph(val a: Point, val b: Point, val dist: Int)