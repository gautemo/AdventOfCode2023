import org.junit.jupiter.api.Assertions.*
import shared.Input
import kotlin.test.Test

class Day16KtTest {
    private val input = Input("""
        .|...\....
        |.-.\.....
        .....|-...
        ........|.
        ..........
        .........\
        ..../.\\..
        .-.-/..|..
        .|....-|.\
        ..//.|....
    """.trimIndent())


    @Test
    fun day16A() {
        val result = day16A(input)
        assertEquals(46, result)
    }

    @Test
    fun day16B() {
        val result = day16B(input)
        assertEquals(51, result)
    }
}