import shared.Input
import shared.Point
import shared.XYMap

fun main() {
    val input = Input.day(16)
    println(day16A(input))
    println(day16B(input))
}

fun day16A(input: Input): Int {
    val map = XYMap(input) { it }
    var beams = listOf(BeamState(Point(0, 0), "right"))
    val history = beams.toMutableList()
    while (beams.isNotEmpty()) {
        var visual = ""
        for(y in 0 ..< map.height) {
            for (x in 0 ..< map.width) {
                visual += if(history.any { b -> b.point == Point(x,y)  }) "#" else map[Point(x,y)]
            }
            visual += "\n"
        }
        //println(visual)

        val nextBeams = mutableListOf<BeamState>()
        beams.forEach {
            nextBeams.addAll(it.move(map[it.point]))
        }
        beams = nextBeams.filter { !history.contains(it) && map.getOrNull(it.point) != null }
        history.addAll(beams)
    }
    return history.map { it.point }.toSet().size
}

fun day16B(input: Input): Int {
    return 0
}

private data class BeamState(val point: Point, val dir: String) {
    fun move(on: Char): List<BeamState> {
        return when {
            (on == '.' || on == '|') && dir == "up" -> listOf(up())
            (on == '.' || on == '-') && dir == "right" -> listOf(right())
            (on == '.' || on == '|') && dir == "down" -> listOf(down())
            (on == '.' || on == '-') && dir == "left" -> listOf(left())
            on == '\\' && dir == "up" -> listOf(left())
            on == '\\' && dir == "right" -> listOf(down())
            on == '\\' && dir == "down" -> listOf(right())
            on == '\\' && dir == "left" -> listOf(up())
            on == '/' && dir == "up" -> listOf(right())
            on == '/' && dir == "right" -> listOf(up())
            on == '/' && dir == "down" -> listOf(left())
            on == '/' && dir == "left" -> listOf(down())
            on == '-' && (dir == "up" || dir == "down") -> {
                listOf(left(), right())
            }
            on == '|' && (dir == "right" || dir == "left") -> {
                listOf(up(), down())
            }
            else -> throw Exception()
        }
    }

    private fun up() = BeamState(Point(point.x, point.y - 1), "up")
    private fun right() = BeamState(Point(point.x + 1, point.y), "right")
    private fun down() = BeamState(Point(point.x, point.y + 1), "down")
    private fun left() = BeamState(Point(point.x - 1, point.y), "left")
}
