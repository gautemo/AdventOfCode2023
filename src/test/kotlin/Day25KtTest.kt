import org.junit.jupiter.api.Assertions.*
import shared.Input
import kotlin.test.Test

class Day25KtTest {
    private val input = Input("""
        jqt: rhn xhk nvd
        rsh: frs pzl lsr
        xhk: hfx
        cmg: qnr nvd lhk bvb
        rhn: xhk bvb hfx
        bvb: xhk hfx
        pzl: lsr hfx nvd
        qnr: nvd
        ntq: jqt hfx bvb xhk
        nvd: lhk
        lsr: lhk
        rzs: qnr cmg lsr rsh
        frs: qnr lhk lsr
    """.trimIndent())


    @Test
    fun day25A() {
        val result = day25A(input)
        assertEquals(54, result)
    }
}