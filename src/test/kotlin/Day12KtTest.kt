import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import shared.Input
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day12KtTest {
    private val input = Input("""
        ???.### 1,1,3
        .??..??...?##. 1,1,3
        ?#?#?#?#?#?#?#? 1,3,1,6
        ????.#...#... 4,1,1
        ????.######..#####. 1,6,5
        ?###???????? 3,2,1
    """.trimIndent())

    private fun springs() = listOf(
        Arguments.of(input.lines[0], 1),
        Arguments.of(input.lines[1], 4),
        Arguments.of(input.lines[2], 1),
        Arguments.of(input.lines[3], 1),
        Arguments.of(input.lines[4], 4),
        Arguments.of(input.lines[5], 10),
    )

    @ParameterizedTest
    @MethodSource("springs")
    fun `test spring arrangement`(input: String, expected: Long) {
        val result = countPermutes(input)
        assertEquals(expected, result)
    }

    @Test
    fun day12A() {
        val result = day12A(input)
        assertEquals(21, result)
    }

    @Test
    fun day12B() {
        val result = day12B(input)
        assertEquals(525152, result)
    }
}