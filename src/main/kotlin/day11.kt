import shared.Input
import shared.XYMap

fun main() {
    val input = Input.day(11)
    println(day11A(input))
    println(day11B(input))
}

fun day11A(input: Input): Long {
    val map = XYMap(input) { if(it == '#') it else null }
    return sumGalaxyPairDistance(map)
}

fun day11B(input: Input, emptySize: Long = 1000000): Long {
    val map = XYMap(input) { if(it == '#') it else null }
    return sumGalaxyPairDistance(map, emptySize)
}

private fun sumGalaxyPairDistance(map: XYMap<Char>, emptySize: Long = 2): Long {
    val pairs = map.all().keys.mapIndexed { index, galaxy1 ->
        map.all().keys.drop(index + 1).map { Pair(galaxy1, it)}
    }.flatten()
    return pairs.sumOf { pair ->
        val xRange = minOf(pair.first.x, pair.second.x) ..< maxOf(pair.first.x, pair.second.x)
        val yRange = minOf(pair.first.y, pair.second.y) ..< maxOf(pair.first.y, pair.second.y)
        val emptyHorizontal = xRange.count { x ->
            map.all().keys.none { it.x == x }
        }
        val emptyVertical = yRange.count { y ->
            map.all().keys.none { it.y == y }
        }
        val emptyLines = emptyHorizontal + emptyVertical
        xRange.count() + yRange.count() + emptyLines * emptySize - emptyLines
    }
}
