import shared.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = Input.day(24)
    println(day24A(input))
    println(day24B(input))
}

fun day24A(input: Input, min: Long = 200000000000000, max: Long = 400000000000000): Int {
    val hailstones = input.lines.map { line ->
        line.toLongs().let {
            Hailstone(it[0], it[1], it[2], it[3].toInt(), it[4].toInt(), it[5].toInt())
        }
    }
    var count = 0
    hailstones.forEachIndexed { index, h1 ->
        hailstones.drop(index + 1).forEach { h2 ->
            intersect(h1.px, h1.vx, h1.py, h1.vy, h2.px, h2.vx, h2.py, h2.vy)?.let {
                if (min <= it.first && it.first <= max && min <= it.second && it.second <= max) {
                    count++
                }
            }
        }
    }
    return count
}

fun day24B(input: Input): Long {
    val hailstones = input.lines.map { line ->
        line.toLongs().let {
            Hailstone(it[0], it[1], it[2], it[3].toInt(), it[4].toInt(), it[5].toInt())
        }
    }
    return findIntersect(hailstones)
}

private fun findIntersect(hailstones: List<Hailstone>): Long {
    val checkVelocity = 500
    for(vx in -checkVelocity .. checkVelocity) {
        for(vy in -checkVelocity .. checkVelocity) {
            for(vz in -checkVelocity .. checkVelocity) {
                val relativeVelocity1x = hailstones[0].vx - vx
                val relativeVelocity1y = hailstones[0].vy - vy
                val relativeVelocity2x = hailstones[1].vx - vx
                val relativeVelocity2y = hailstones[1].vy - vy
                val slopeDiff = relativeVelocity1x * relativeVelocity2y - relativeVelocity1y * relativeVelocity2x
                if(slopeDiff != 0) {
                    val t = (relativeVelocity2y * (hailstones[1].px - hailstones[0].px) - relativeVelocity2x * (hailstones[1].py - hailstones[0].py)) / slopeDiff
                    if(t >= 0) {
                        val x = hailstones[0].px + (hailstones[0].vx - vx) * t
                        val y = hailstones[0].py + (hailstones[0].vy - vy) * t
                        val z = hailstones[0].pz + (hailstones[0].vz - vz) * t
                        val hitAll = hailstones.all {
                            it.willColide(Hailstone(x, y, z, vx, vy, vz))
                        }
                        if(hitAll) {
                            return x + y + z
                        }
                    }
                }
            }
        }
    }
    throw Exception()
}

private class Hailstone(val px: Long, val py: Long, val pz: Long, val vx: Int, val vy: Int, val vz: Int) {
    fun willColide(other: Hailstone): Boolean {
        val t = when {
            vx != other.vx -> (other.px - px).toDouble() / (vx - other.vx)
            vy != other.vy -> (other.py - py).toDouble() / (vy - other.vy)
            vz != other.vz -> (other.pz - pz).toDouble() / (vz - other.vz)
            else -> return false
        }
        if (t < 0) return false
        return positionAfterTime(t) == other.positionAfterTime(t)
    }

    fun positionAfterTime(time: Double): Triple<Double, Double, Double> = Triple(
        first = px + vx * time,
        second = py + vy * time,
        third = pz + vz * time,
    )
}

private fun intersect(
    px1: Long,
    vx1: Int,
    py1: Long,
    vy1: Int,
    px2: Long,
    vx2: Int,
    py2: Long,
    vy2: Int,
): Pair<Float, Float>? {
    val a1 = vy1.toFloat()
    val b1 = -vx1.toFloat()
    val c1 = (vy1 * px1 - vx1 * py1).toFloat()
    val a2 = vy2.toFloat()
    val b2 = -vx2.toFloat()
    val c2 = (vy2 * px2 - vx2 * py2).toFloat()
    if (a1 * b2 == b1 * a2) {
        // parallel
        return null
    }
    val x = (c1 * b2 - c2 * b1) / (a1 * b2 - a2 * b1)
    val y = (c2 * a1 - c1 * a2) / (a1 * b2 - a2 * b1)
    if ((x - px1) * vx1 >= 0 && (y - py1) * vy1 >= 0) {
        if ((x - px2) * vx2 >= 0 && (y - py2) * vy2 >= 0) {
            return x to y
        }
    }
    return null
}
