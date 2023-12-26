import shared.*

fun main() {
    val input = Input.day(9)
    println(day9A(input))
    println(day9B(input))
}

fun day9A(input: Input): Long {
    val sequences = input.lines.map { Sequence(it.toLongs().toMutableList()) }
    sequences.forEach { it.addNextValue() }
    return sequences.sumOf { it.values.last() }
}

fun day9B(input: Input): Long {
    val sequences = input.lines.map { Sequence(it.toLongs().toMutableList()) }
    sequences.forEach { it.addPreviousValue() }
    return sequences.sumOf { it.values[0] }
}

class Sequence(val values: MutableList<Long>) {
    fun addNextValue() {
        if(values.all { it == 0L }) {
            values.add(0)
        } else {
            val subSequence = Sequence(values.zipWithNext().map { it.second - it.first }.toMutableList())
            subSequence.addNextValue()
            values.add(values.last() + subSequence.values.last())
        }
    }

    fun addPreviousValue() {
        if(values.all { it == 0L }) {
            values.add(0, 0)
        } else {
            val subSequence = Sequence(values.zipWithNext().map { it.second - it.first }.toMutableList())
            subSequence.addPreviousValue()
            values.add(0, values[0] - subSequence.values[0])
        }
    }
}