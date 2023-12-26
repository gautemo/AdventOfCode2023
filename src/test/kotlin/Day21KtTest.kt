import org.junit.jupiter.api.Assertions.*
import shared.Input
import kotlin.test.Test

class Day21KtTest {
    private val input = Input("""
        ...........
        .....###.#.
        .###.##..#.
        ..#.#...#..
        ....#.#....
        .##..S####.
        .##..#...#.
        .......##..
        .##.#.####.
        .##..##.##.
        ...........
    """.trimIndent())


    @Test
    fun day21A() {
        val result = day21A(input, 6)
        assertEquals(16, result)
    }

    @Test
    fun day21B() {
        val result = day21B(input, 100)
        assertEquals(6536, result)
    }
}