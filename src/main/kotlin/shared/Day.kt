package shared

import java.io.File

fun day(nr: Int): Input {
    return Input(
        File(
            Thread.currentThread()
                .contextClassLoader
                .getResource("day$nr.txt")!!
                .toURI()
        ).readText().trimEnd()
    )
}

class Input(private val text: String) {
    val lines: List<String>
        get() = text.lines()
    val chunks: List<String>
        get() = text.split("\n\n")
}