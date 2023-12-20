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
    return tilesEnergized(map, BeamState(Point(0, 0), "right"))
}

fun day16B(input: Input): Int {
    val map = XYMap(input) { it }
    val entries = map.all().filter { it.key.x == 0 }.map { BeamState(it.key, "right") } +
            map.all().filter { it.key.x == map.width - 1 }.map { BeamState(it.key, "left") } +
            map.all().filter { it.key.y == 0 }.map { BeamState(it.key, "down") } +
            map.all().filter { it.key.y == map.height - 1 }.map { BeamState(it.key, "up") }
    return entries.maxOf {
        tilesEnergized(map, it)
    }
}

private fun tilesEnergized(map: XYMap<Char>, start: BeamState): Int {
    var beams = listOf(start)
    val history = beams.toMutableList()
    while (beams.isNotEmpty()) {
        val nextBeams = mutableListOf<BeamState>()
        beams.forEach {
            nextBeams.addAll(it.move(map[it.point]))
        }
        beams = nextBeams.filter { !history.contains(it) && map.getOrNull(it.point) != null }
        history.addAll(beams)
    }
    return history.map { it.point }.toSet().size
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
