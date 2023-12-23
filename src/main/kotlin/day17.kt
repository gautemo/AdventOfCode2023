import shared.Input
import shared.Point
import shared.XYMap

/* I'ts slow, but it both A and B completes in 30 minutes */
fun main() {
    val input = Input.day(17)
    println(day17A(input))
    println(day17B(input))
}

fun day17A(input: Input): Int {
    val map = XYMap(input) { it.digitToInt() }
    return aStarIsh(map)
}

fun day17B(input: Input): Int {
    val map = XYMap(input) { it.digitToInt() }
    return aStarIsh(map, ultra = true)
}

private fun aStarIsh(map: XYMap<Int>, ultra: Boolean = false): Int {
    fun Crucible.heuristic() = ((map.width - at.x) + (map.height - at.y)) * 10

    val start = Crucible(Point(0, 0), "right", 0, ultra)
    val openSet = mutableSetOf(start)
    val cameFrom = mutableMapOf<Crucible, Crucible>()
    val gScore = mutableMapOf(start to 0)
    val fScore = mutableMapOf(start to start.heuristic())

    fun reconstructPath(end: Crucible): Int {
        var totalPath = 0
        var current = end
        while (cameFrom.contains(current)) {
            totalPath += map[current.at]
            current = cameFrom[current]!!
        }
        return totalPath
    }

    var best = Int.MAX_VALUE
    while (openSet.isNotEmpty()) {
        val current = openSet.minBy { it.heuristic() }.also { openSet.remove(it) }
        if(current.at == Point(map.width - 1, map.height - 1) && (!ultra || current.straight >= 4)) {
            best = minOf(best, reconstructPath(current))
        } else {
            current.nextOptions().filter { map.getOrNull(it.at) != null }.forEach { neighbor ->
                val tentativeGScore = gScore[current]!! + map[neighbor.at]
                if(tentativeGScore < (gScore[neighbor] ?: Int.MAX_VALUE) && tentativeGScore < best) {
                    cameFrom[neighbor] = current
                    gScore[neighbor] = tentativeGScore
                    fScore[neighbor] = tentativeGScore + neighbor.heuristic()
                    openSet.add(neighbor)
                }
            }
        }
    }
    return best
}

private data class Crucible(val at: Point, val dir: String, val straight: Int, val ultra: Boolean) {
    fun nextOptions(): List<Crucible> {
        return when(dir) {
            "up" -> listOf(
                Crucible(at.left(), "left", 1, ultra),
                Crucible(at.up(), "up", straight + 1, ultra),
                Crucible(at.right(), "right", 1, ultra),
            )
            "right" -> listOf(
                Crucible(at.up(), "up", 1, ultra),
                Crucible(at.right(), "right", straight + 1, ultra),
                Crucible(at.down(), "down", 1, ultra),
            )
            "down" -> listOf(
                Crucible(at.right(), "right", 1, ultra),
                Crucible(at.down(), "down", straight + 1, ultra),
                Crucible(at.left(), "left", 1, ultra),
            )
            "left" -> listOf(
                Crucible(at.down(), "down", 1, ultra),
                Crucible(at.left(), "left", straight + 1, ultra),
                Crucible(at.up(), "up", 1, ultra),
            )
            else -> throw Exception()
        }.filter {
            if(!ultra) {
                it.straight <= 3
            } else if(straight < 4){
                it.dir == dir
            } else {
                it.straight <= 10
            }
        }
    }
}

