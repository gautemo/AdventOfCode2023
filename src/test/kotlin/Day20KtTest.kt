import org.junit.jupiter.api.Assertions.*
import shared.Input
import kotlin.test.Test

class Day20KtTest {
    private val input = Input("""
        broadcaster -> a
        %a -> inv, con
        &inv -> b
        %b -> con
        &con -> output
    """.trimIndent())


    @Test
    fun day20A() {
        val result = day20A(input)
        assertEquals(11687500, result)
    }
}