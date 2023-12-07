import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import shared.Input

class Day7KtTest {
    private val input = Input("""
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
    """.trimIndent())

    @Test
    fun day7A() {
        val result = day7A(input)
        assertEquals(6440, result)
    }

    @Test
    fun day7B() {
        val result = day7B(input)
        assertEquals(5902, result)
    }
}