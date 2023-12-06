import shared.*

fun main() {
    val input = Input.day(6)
    println(day6A(input))
    println(day6B(input))
}

fun day6A(input: Input): Int {
    val times = input.lines[0].toLongs()
    val records = input.lines[1].toLongs()
    val winOptions = times.mapIndexed { index, time ->
        raceWinOptions(time, records[index])
    }
    return winOptions.reduce { acc, i -> acc * i }
}

fun day6B(input: Input): Int {
    val time = input.lines[0].replace(" ", "").split(':')[1].toLong()
    val record = input.lines[1].replace(" ", "").split(':')[1].toLong()
    return raceWinOptions(time, record)
}

private fun raceWinOptions(time: Long, record: Long): Int {
    var count = 0
    for(charge in 1 .. time) {
        val moved = (time - charge) * charge
        if(moved > record) count++
    }
    return count
}
