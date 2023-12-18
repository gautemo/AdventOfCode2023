import org.junit.jupiter.api.Assertions.*
import shared.Input
import kotlin.test.Test

class Day15KtTest {
    private val input = Input("""
        rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7
    """.trimIndent())


    @Test
    fun day15A() {
        val result = day15A(input)
        assertEquals(1320, result)
    }

    @Test
    fun day15B() {
        val result = day15B(input)
        assertEquals(145, result)
    }
}