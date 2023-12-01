import shared.Input
import shared.day

fun main() {
    val input = day(1)
    println(day1A(input))
    println(day1B(input))
}

fun day1A(input: Input) = calibration(input.lines)

fun day1B(input: Input): Int{
    return calibration(
        input.lines.map {
            it
                .replace("one", "one1one")
                .replace("two", "two2two")
                .replace("three", "three3three")
                .replace("four", "four4four")
                .replace("five", "five5five")
                .replace("six", "six6six")
                .replace("seven", "seven7seven")
                .replace("eight", "eight8eight")
                .replace("nine", "nine9nine")
        }
    )
}

fun calibration(lines: List<String>): Int {
    return lines.sumOf { line ->
        val first = line.first { it.isDigit() }
        val last = line.last { it.isDigit() }
        "$first$last".toInt()
    }
}