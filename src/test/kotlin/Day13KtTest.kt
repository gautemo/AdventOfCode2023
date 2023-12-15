import org.junit.jupiter.api.Assertions.*
import shared.Input
import kotlin.test.Test

class Day13KtTest {
    private val input = Input("""
        #.##..##.
        ..#.##.#.
        ##......#
        ##......#
        ..#.##.#.
        ..##..##.
        #.#.##.#.
        
        #...##..#
        #....#..#
        ..##..###
        #####.##.
        #####.##.
        ..##..###
        #....#..#
    """.trimIndent())


    @Test
    fun day13A() {
        val result = day13A(input)
        assertEquals(405, result)
    }

    @Test
    fun day13B() {
        val result = day13B(input)
        assertEquals(400, result)
    }
}