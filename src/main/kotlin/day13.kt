import shared.Input
import shared.toInts

fun main() {
    val input = Input.day(13)
    println(day13A(input))
    println(day13B(input))
}

fun day13A(input: Input): Int {
    return input.chunks.sumOf {
        reflectsVertically(it) ?: (reflectsHorizontally(it)!! * 100)
    }
}

fun day13B(input: Input): Int {
    return input.chunks.sumOf {
        val avoid = reflectsVertically(it) ?: (reflectsHorizontally(it)!! * 100)
        for(x in 0 ..< it.lines()[0].length) {
            for(y in 0 ..< it.lines().size) {
                val replaceAt = x + (y * (it.lines()[0].length + 1))
                val altered = it.replaceRange(replaceAt, replaceAt + 1, if(it[replaceAt] == '.') "x" else ".")
                reflectsVertically(altered, avoid)?.let { found ->
                    return@sumOf found
                }
                reflectsHorizontally(altered, avoid)?.let { found ->
                    return@sumOf found * 100
                }
            }
        }
        throw Exception()
    }
}

private fun reflectsVertically(patterns: String, avoid: Int? = null): Int? {
    for(i in 1 ..< patterns.lines()[0].length) {
        val reflected = patterns.lines().all {
            val right = it.substring(i).take(i)
            val left = it.take(i).takeLast(right.length)
            left == right.reversed()
        }
        if(reflected && i != avoid) return i
    }
    return null
}

private fun reflectsHorizontally(patterns: String, avoid: Int? = null): Int? {
    var flipped = ""
    for(x in 0 ..< patterns.lines()[0].length) {
        patterns.lines().forEach { flipped += it[x] }
        flipped += "\n"
    }
    return reflectsVertically(flipped.trim(), avoid?.let { it / 100 })
}