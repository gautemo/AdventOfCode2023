import org.junit.jupiter.api.Assertions.*
import shared.Input
import kotlin.test.Test

class Day18KtTest {
    private val input = Input("""
        R 6 (#70c710)
        D 5 (#0dc571)
        L 2 (#5713f0)
        D 2 (#d2c081)
        R 2 (#59c680)
        D 2 (#411b91)
        L 5 (#8ceee2)
        U 2 (#caa173)
        L 1 (#1b58a2)
        U 2 (#caa171)
        R 2 (#7807d2)
        U 3 (#a77fa3)
        L 2 (#015232)
        U 2 (#7a21e3)
    """.trimIndent())


    @Test
    fun day18A() {
        val result = day18A(input)
        assertEquals(62, result)
    }

    @Test
    fun day18B() {
        val result = day18B(input)
        assertEquals(952408144115, result)
    }
}