import org.junit.jupiter.api.Assertions.*
import shared.Input
import kotlin.test.Test

class Day24KtTest {
    private val input = Input("""
        19, 13, 30 @ -2,  1, -2
        18, 19, 22 @ -1, -1, -2
        20, 25, 34 @ -2, -2, -4
        12, 31, 28 @ -1, -2, -1
        20, 19, 15 @  1, -5, -3
    """.trimIndent())


    @Test
    fun day24A() {
        val result = day24A(input, 7, 27)
        assertEquals(2, result)
    }

    @Test
    fun day24B() {
        val result = day24B(input)
        assertEquals(47, result)
    }
}