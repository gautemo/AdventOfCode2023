import org.junit.jupiter.api.Assertions.*
import shared.Input
import kotlin.test.Test

class Day9KtTest {
    private val input = Input("""
        0 3 6 9 12 15
        1 3 6 10 15 21
        10 13 16 21 30 45
    """.trimIndent())


    @Test
    fun day9A() {
        val result = day9A(input)
        assertEquals(114, result)
    }

    @Test
    fun day9B() {
        val result = day9B(input)
        assertEquals(2, result)
    }
}