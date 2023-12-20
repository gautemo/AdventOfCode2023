import shared.Input
import shared.toInts
import kotlin.math.pow

fun main() {
    val input = Input.day(4)
    println(day4A(input))
    println(day4B(input))
}

fun day4A(input: Input): Int {
    return input.lines.sumOf { card ->
        val (winning, yours) = card.split(':')[1].split('|').map(String::toInts)
        val match = winning.intersect(yours.toSet()).size
        val points = if(match == 0) 0 else 2.toDouble().pow(match - 1)
        points.toInt()
    }
}

fun day4B(input: Input): Int {
    val cards = MutableList(input.lines.size) { 1 }
    input.lines.forEachIndexed { index, card ->
        val (winning, yours) = card.split(':')[1].split('|').map(String::toInts)
        val match = winning.intersect(yours.toSet()).size
        repeat(match) {
            cards[index + it + 1] += cards[index]
        }
    }
    return cards.sum()
}
