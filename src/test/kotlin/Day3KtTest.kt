import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import shared.Input

class Day3KtTest {
    private val input = Input("""
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...${'$'}.*....
        .664.598..
    """.trimIndent())

    @Test
    fun day3A() {
        val result = day3A(input)
        assertEquals(4361, result)
    }

    @Test
    fun day3B() {
        val result = day3B(input)
        assertEquals(467835, result)
    }
}