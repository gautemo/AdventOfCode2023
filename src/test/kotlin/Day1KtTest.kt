import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import shared.Input

class Day1KtTest {
    private val inputA = Input("""
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet
    """.trimIndent())
    private val inputB = Input("""
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen
    """.trimIndent())

    @Test
    fun partA() {
        val result = day1A(inputA)
        assertEquals(142, result)
    }

    @Test
    fun partB() {
        val result = day1B(inputB)
        assertEquals(281, result)
    }
}