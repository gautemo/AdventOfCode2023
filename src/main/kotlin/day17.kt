import shared.*

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
    fun Mover.heuristic() = ((map.width - at.x) + (map.height - at.y)) * 10

    val start = Mover(Point(0, 0), Right, 0)
    val openSet = mutableSetOf(start)
    val cameFrom = mutableMapOf<Mover, Mover>()
    val gScore = mutableMapOf(start to 0)
    val fScore = mutableMapOf(start to start.heuristic())

    fun reconstructPath(end: Mover): Int {
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
        if(current.at == Point(map.width - 1, map.height - 1) && (!ultra || current.steps!! >= 4)) {
            best = minOf(best, reconstructPath(current))
        } else {
            current.nextOptions(ultra).filter { map.getOrNull(it.at) != null }.forEach { neighbor ->
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

private fun Mover.nextOptions(ultra: Boolean): List<Mover> {
    return when(dir) {
        Up -> listOf(
            left().apply { steps = 1 },
            up(),
            right().apply { steps = 1 },
        )
        Right -> listOf(
            up().apply { steps = 1 },
            right(),
            down().apply { steps = 1 },
        )
        Down -> listOf(
            right().apply { steps = 1 },
            down(),
            left().apply { steps = 1 },
        )
        Left -> listOf(
            down().apply { steps = 1 },
            left(),
            up().apply { steps = 1 },
        )
    }.filter {
        if(!ultra) {
            it.steps!! <= 3
        } else if(steps!! < 4){
            it.dir == dir
        } else {
            it.steps!! <= 10
        }
    }
}

