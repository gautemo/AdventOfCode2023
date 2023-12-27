import shared.*
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = Input.day(22)
    println(day22A(input))
    println(day22B(input))
}

fun day22A(input: Input): Int {
    val bricks = input.lines.map(Brick::init)
    bricks.fall()
    return bricks.count { brick ->
        bricks.none { b -> b.canFall(bricks.filter { it != b && it != brick }) }
    }
}

fun day22B(input: Input): Int {
    val bricks = input.lines.map(Brick::init)
    bricks.fall()
    return bricks.sumOf { brick ->
        val withoutBrick = bricks
            .filter { it != brick }
            .map { Brick(it.x, it.y, it.currentZ) }
        withoutBrick.fall()
        withoutBrick.count { it.hasFallen() }
    }
}

private class Brick(val x: Range, val y: Range, val z: Range) {
    var currentZ = z

    fun canFall(others: List<Brick>): Boolean {
        val lowerZ = Range(currentZ.first - 1, currentZ.last - 1)
        if(lowerZ.contains(0)) return false
        return others.none {
            x.intersect(it.x) && y.intersect(it.y) && lowerZ.intersect(it.currentZ)
        }
    }

    fun fall() {
        currentZ = Range(currentZ.first - 1, currentZ.last - 1)
    }

    fun hasFallen() = z != currentZ

    companion object {
        fun init(line: String): Brick {
            val ints = line.toInts()
            return Brick(
                Range(ints[0], ints[3]),
                Range(ints[1], ints[4]),
                Range(ints[2], ints[5]),
            )
        }
    }
}

private fun List<Brick>.fall() {
    while (any { b -> b.canFall(filter { it != b }) }) {
        forEach { b ->
            if(b.canFall(filter { it != b })) {
                b.fall()
            }
        }
    }
}

private data class Range(private val a: Int, private val b: Int) {
    val first = min(a, b)
    val last = max(a, b)

    fun contains(value: Int): Boolean {
        return first <= value && value <= last
    }

    fun intersect(other: Range): Boolean {
        return contains(other.first) || contains(other.last) || other.contains(first) || other.contains(last)
    }
}