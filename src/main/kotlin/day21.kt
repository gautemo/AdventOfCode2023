import shared.*

fun main() {
    val input = Input.day(21)
    println(day21A(input))
    println(day21B(input))
}

fun day21A(input: Input, steps: Int = 64): Long {
    val map = InfinityMap(input)
    return possibleGardenPlots(map, steps)
}

/* Runs in a few hours */
fun day21B(input: Input, steps: Int = 26501365): Long {
    val map = InfinityMap(input)
    return possibleGardenPlots(map, steps)
}

private fun Char.isMovable() = this == 'S' || this == '.'

/* Let's pretend I got 65 and 131 from the length of the map and not Reddit */
private fun possibleGardenPlots(map: InfinityMap, steps: Int): Long {
    val sequence = mutableListOf<Long>()
    repeat(steps) { step ->
        if((step - 65) % 131 == 0) {
            sequence.add(map.reachable().toLong())
            if (sequence.size == 3) {
                val sequenceFixer = Sequence(sequence)
                repeat((steps - step) / 131) {
                    sequenceFixer.addNextValue()
                }
                return sequenceFixer.values.last()
            }
        }
        map.walk()
    }
    return map.reachable().toLong()
}

private class InfinityMap(private val input: Input) {
    private var on = setOf(Point(input.lines[0].length / 2, input.lines.size / 2))
    val width = input.lines[0].length
    val height = input.lines.size

    fun walk() {
        on = on.flatMap { onPoint ->
            listOf(
                Point(onPoint.x - 1, onPoint.y),
                Point(onPoint.x + 1, onPoint.y),
                Point(onPoint.x, onPoint.y - 1),
                Point(onPoint.x, onPoint.y + 1),
            ).filter {
                input.lines[Math.floorMod(it.y, height)][Math.floorMod(it.x, width)].isMovable()
            }
        }.toSet()
    }

    fun reachable() = on.size
}