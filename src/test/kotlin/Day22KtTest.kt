import org.junit.jupiter.api.Assertions.*
import shared.Input
import kotlin.test.Test

class Day22KtTest {
    private val input = Input("""
        1,0,1~1,2,1
        0,0,2~2,0,2
        0,2,3~2,2,3
        0,0,4~0,2,4
        2,0,5~2,2,5
        0,1,6~2,1,6
        1,1,8~1,1,9
    """.trimIndent())


    @Test
    fun day22A() {
        val result = day22A(input)
        assertEquals(5, result)
    }

    @Test
    fun day22B() {
        val result = day22B(input)
        assertEquals(7, result)
    }
}