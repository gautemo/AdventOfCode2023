import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import shared.Input

class Day6KtTest {
    private val input = Input("""
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent())

    @Test
    fun day6A() {
        val result = day6A(input)
        assertEquals(288, result)
    }

    @Test
    fun day6B() {
        val result = day6B(input)
        assertEquals(71503, result)
    }
}