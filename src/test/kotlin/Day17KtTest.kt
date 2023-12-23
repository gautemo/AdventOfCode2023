import org.junit.jupiter.api.Assertions.*
import shared.Input
import kotlin.test.Test

class Day17KtTest {
    private val input = Input("""
        2413432311323
        3215453535623
        3255245654254
        3446585845452
        4546657867536
        1438598798454
        4457876987766
        3637877979653
        4654967986887
        4564679986453
        1224686865563
        2546548887735
        4322674655533
    """.trimIndent())


    @Test
    fun day17A() {
        val result = day17A(input)
        assertEquals(102, result)
    }

    @Test
    fun day17B() {
        val result = day17B(input)
        assertEquals(94, result)
    }
}