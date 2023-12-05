import shared.*

fun main() {
    val input = Input.day(5)
    println(day5A(input))
    println(day5B(input))
}

fun day5A(input: Input): Long {
    val seeds = input.chunks[0].toLongs()
    val categories = input.chunks.drop(1).map(Category::from)
    val mapped = seeds.map { seed ->
        categories.fold(seed) { value, category ->
            category.map(value)
        }
    }
    return mapped.min()
}

fun day5B(input: Input): Long {
    var min = Long.MAX_VALUE
    val seeds = input.chunks[0].toLongs().chunked(2)
    val categories = input.chunks.drop(1).map(Category::from)
    seeds.forEach { seed ->
        for (i in seed[0]..<seed[0] + seed[1]) {
            val value = categories.fold(i) { value, category ->
                category.map(value)
            }
            min = minOf(min, value)
        }
    }
    return min
}

private class Category(val converters: List<Converter>) {
    fun map(value: Long): Long {
        return converters.firstOrNull { it.inRange(value) }?.mappedTo(value) ?: value
    }

    companion object {
        fun from(chunk: String): Category {
            return Category(chunk.lines().drop(1).map(Converter::from))
        }
    }
}

private class Converter(val destination: Long, val source: Long, val range: Long) {
    fun inRange(value: Long) = value >= source && value < source + range
    fun mappedTo(value: Long) = destination + value - source

    companion object {
        fun from(line: String): Converter {
            val (destination, source, range) = line.toLongs()
            return Converter(destination, source, range)
        }
    }
}