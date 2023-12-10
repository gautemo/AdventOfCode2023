import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import shared.Input
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day10KtTest {
    private fun inputsA() = listOf(
        Arguments.of(
            Input("""
                -L|F7
                7S-7|
                L|7||
                -L-J|
                L|-JF
            """.trimIndent()),
            4
        ),
        Arguments.of(
            Input("""
                7-F7-
                .FJ|7
                SJLL7
                |F--J
                LJ.LJ
            """.trimIndent()),
            8
        )
    )

    @ParameterizedTest
    @MethodSource("inputsA")
    fun day10A(input: Input, expected: Int) {
        val result = day10A(input)
        assertEquals(expected, result)
    }

    private fun inputsB() = listOf(
        Arguments.of(
            Input("""
                ...........
                .S-------7.
                .|F-----7|.
                .||.....||.
                .||.....||.
                .|L-7.F-J|.
                .|..|.|..|.
                .L--J.L--J.
                ...........
            """.trimIndent()),
            4
        ),
        Arguments.of(
            Input("""
                ..........
                .S------7.
                .|F----7|.
                .||....||.
                .||....||.
                .|L-7F-J|.
                .|..||..|.
                .L--JL--J.
                ..........
            """.trimIndent()),
            4
        ),
        Arguments.of(
            Input("""
                .F----7F7F7F7F-7....
                .|F--7||||||||FJ....
                .||.FJ||||||||L7....
                FJL7L7LJLJ||LJ.L-7..
                L--J.L7...LJS7F-7L7.
                ....F-J..F7FJ|L7L7L7
                ....L7.F7||L7|.L7L7|
                .....|FJLJ|FJ|F7|.LJ
                ....FJL-7.||.||||...
                ....L---J.LJ.LJLJ...
            """.trimIndent()),
            8
        ),
        Arguments.of(
            Input("""
                FF7FSF7F7F7F7F7F---7
                L|LJ||||||||||||F--J
                FL-7LJLJ||||||LJL-77
                F--JF--7||LJLJ7F7FJ-
                L---JF-JLJ.||-FJLJJ7
                |F|F-JF---7F7-L7L|7|
                |FFJF7L7F-JF7|JL---7
                7-L-JL7||F7|L7F-7F7|
                L.L7LFJ|||||FJL7||LJ
                L7JLJL-JLJLJL--JLJ.L
            """.trimIndent()),
            10
        ),
    )

    @ParameterizedTest
    @MethodSource("inputsB")
    fun day10B(input: Input, expected: Int) {
        val result = day10B(input)
        assertEquals(expected, result)
    }
}