import org.junit.jupiter.api.Assertions.*
import shared.Input
import kotlin.test.Test

class Day14KtTest {
    private val input = Input("""
        O....#....
        O.OO#....#
        .....##...
        OO.#O....O
        .O.....O#.
        O.#..O.#.#
        ..O..#O..O
        .......O..
        #....###..
        #OO..#....
    """.trimIndent())


    @Test
    fun day14A() {
        val result = day14A(input)
        assertEquals(136, result)
    }

    @Test
    fun day14B() {
        val result = day14B(input)
        assertEquals(64, result)
    }
}