import shared.*

fun main() {
    val input = Input.day(16)
    println(day16A(input))
    println(day16B(input))
}

fun day16A(input: Input): Int {
    val map = XYMap(input) { it }
    return tilesEnergized(map, Mover(Point(0, 0), Right))
}

fun day16B(input: Input): Int {
    val map = XYMap(input) { it }
    val entries = map.all().filter { it.key.x == 0 }.map { Mover(it.key, Right) } +
            map.all().filter { it.key.x == map.width - 1 }.map { Mover(it.key, Left) } +
            map.all().filter { it.key.y == 0 }.map { Mover(it.key, Down) } +
            map.all().filter { it.key.y == map.height - 1 }.map { Mover(it.key, Up) }
    return entries.maxOf {
        tilesEnergized(map, it)
    }
}

private fun tilesEnergized(map: XYMap<Char>, start: Mover): Int {
    var beams = listOf(start)
    val history = beams.toMutableList()
    while (beams.isNotEmpty()) {
        val nextBeams = mutableListOf<Mover>()
        beams.forEach {
            nextBeams.addAll(it.move(map[it.at]))
        }
        beams = nextBeams.filter { !history.contains(it) && map.getOrNull(it.at) != null }
        history.addAll(beams)
    }
    return history.map { it.at }.toSet().size
}

private fun Mover.move(on: Char): List<Mover> {
    return when {
        (on == '.' || on == '|') && dir == Up -> listOf(up())
        (on == '.' || on == '-') && dir == Right -> listOf(right())
        (on == '.' || on == '|') && dir == Down -> listOf(down())
        (on == '.' || on == '-') && dir == Left -> listOf(left())
        on == '\\' && dir == Up -> listOf(left())
        on == '\\' && dir == Right -> listOf(down())
        on == '\\' && dir == Down -> listOf(right())
        on == '\\' && dir == Left -> listOf(up())
        on == '/' && dir == Up -> listOf(right())
        on == '/' && dir == Right -> listOf(up())
        on == '/' && dir == Down -> listOf(left())
        on == '/' && dir == Left -> listOf(down())
        on == '-' && (dir == Up || dir == Down) -> {
            listOf(left(), right())
        }
        on == '|' && (dir == Right|| dir == Left) -> {
            listOf(up(), down())
        }
        else -> throw Exception()
    }
}
