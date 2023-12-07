import shared.*

/* WARNING only part 1, and not cleaned up */

fun main() {
    val input = Input.day(7)
    println(day7A(input))
    println(day7B(input))
}

fun day7A(input: Input): Int {
    val hands = input.lines.map { Hand(it) }
    return hands.sorted().mapIndexed { index, hand ->
        hand.bid * (index + 1)
    }.sum()
}

fun day7B(input: Input): Int {
    return 0
}

private class Hand(input: String): Comparable<Hand> {
    val hand = input.split(' ')[0]
    val bid = input.split(' ')[1].toInt()
    val type: Type
    private val cardValues = mapOf(
        'T' to 10,
        'J' to 11,
        'Q' to 12,
        'K' to 13,
        'A' to 15,
    )

    init {
        val times = hand.groupBy { it }.map { it.value.size }
        type = when {
            times.contains(5) -> Type.FiveOfAKind
            times.contains(4) -> Type.FourOfAKind
            times.contains(3) && times.contains(2) -> Type.FullHouse
            times.contains(3) -> Type.ThreeOfAKind
            times.count { it == 2 } == 2 -> Type.TwoPair
            times.contains(2) -> Type.OnePair
            else -> Type.HighCard
        }
    }

    override fun compareTo(other: Hand): Int {
        val typeCompare = type.value.compareTo(other.type.value)
        if(typeCompare != 0) {
            return typeCompare
        }
        val cardsCompared = hand.mapIndexed { index, c ->
            val value = cardValues[c] ?: c.digitToInt()
            val otherValue = cardValues[other.hand[index]] ?: other.hand[index].digitToInt()
            value.compareTo(otherValue)
        }
        return cardsCompared.firstOrNull { it != 0 } ?: 0
    }

}

private enum class Type(val value: Int) {
    FiveOfAKind(7),
    FourOfAKind(6),
    FullHouse(5),
    ThreeOfAKind(4),
    TwoPair(3),
    OnePair(2),
    HighCard(1),
}