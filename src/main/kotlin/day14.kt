import shared.Input
import shared.Point
import shared.XYMap

fun main() {
    val input = Input.day(14)
    println(day14A(input))
    println(day14B(input))
}

fun day14A(input: Input): Int {
    val platform = Platform(input)
    platform.roll(0, -1)
    return platform.loadNorthSupportBeams()
}

fun day14B(input: Input): Int {
    val platform = Platform(input)
    val history = mutableListOf<Set<Point>>()
    val repeat = 1000000000
    var count = 0
    var cycleFound = false
    while (count < repeat) {
        platform.roll(0, -1)
        platform.roll(-1, 0)
        platform.roll(0, 1)
        platform.roll(1, 0)
        if(history.contains(platform.snapshot()) && !cycleFound) {
            cycleFound = true
            val cycle = count - history.indexOf(platform.snapshot())
            val rest = repeat - count
            count += cycle * ((rest / cycle)-1)
        } else {
            history.add(platform.snapshot())
        }
        count++
    }
    return platform.loadNorthSupportBeams()
}

private class Platform(input: Input) {
    val map = XYMap(input) { it }

    fun roll(dirX: Int, dirY: Int) {
        do {
            val rocks = map.all { it == 'O' }.keys
            rocks.forEach {
                val rollsTo = Point(it.x + dirX, it.y + dirY)
                if(map.getOrNull(rollsTo) == '.') {
                    map[rollsTo] = 'O'
                    map[it] = '.'
                }
            }
        } while (rocks != map.all { it == 'O' }.keys)
    }

    fun loadNorthSupportBeams(): Int {
        return map.all { it == 'O' }.keys.sumOf {
            map.height - it.y
        }
    }

    fun snapshot() = map.all { it == 'O' }.keys
}