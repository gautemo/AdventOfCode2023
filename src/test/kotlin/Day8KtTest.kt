import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import shared.Input
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day8KtTest {
    private fun inputs() = listOf(
        Arguments.of(
            Input("""
                RL
    
                AAA = (BBB, CCC)
                BBB = (DDD, EEE)
                CCC = (ZZZ, GGG)
                DDD = (DDD, DDD)
                EEE = (EEE, EEE)
                GGG = (GGG, GGG)
                ZZZ = (ZZZ, ZZZ)
            """.trimIndent()),
            2
        ),
        Arguments.of(
            Input("""
                LLR

                AAA = (BBB, BBB)
                BBB = (AAA, ZZZ)
                ZZZ = (ZZZ, ZZZ)
            """.trimIndent()),
            6
        )
    )


    @ParameterizedTest
    @MethodSource("inputs")
    fun day8A(input: Input, expected: Long) {
        val result = day8A(input)
        assertEquals(expected, result)
    }

    @Test
    fun day8B() {
        val result = day8B(Input("""
            LR

            11A = (11B, XXX)
            11B = (XXX, 11Z)
            11Z = (11B, XXX)
            22A = (22B, XXX)
            22B = (22C, 22C)
            22C = (22Z, 22Z)
            22Z = (22B, 22B)
            XXX = (XXX, XXX)
        """.trimIndent()))
        assertEquals(6L, result)
    }
}