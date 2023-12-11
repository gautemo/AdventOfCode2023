import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import shared.Input
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day11KtTest {
    private val input = Input("""
        ...#......
        .......#..
        #.........
        ..........
        ......#...
        .#........
        .........#
        ..........
        .......#..
        #...#.....
    """.trimIndent())

    @Test
    fun day11A() {
        val result = day11A(input)
        assertEquals(374, result)
    }

    private fun emptySize() = listOf(
        Arguments.of(10, 1030),
        Arguments.of(100, 8410),
    )

    @ParameterizedTest
    @MethodSource("emptySize")
    fun day11B(size: Long, expected: Long) {
        val result = day11B(input, size)
        assertEquals(expected, result)
    }
}