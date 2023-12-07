import shared.*

fun main() {
    val input = Input.day(7)
    println(day7A(input))
    println(day7B(input))
}

fun day7A(input: Input): Int {
    val hands = input.lines.map { Hand(it) }
    return totalWinnings(hands)
}

fun day7B(input: Input): Int {
    val hands = input.lines.map { Hand(it, true) }
    return totalWinnings(hands)
}

private fun totalWinnings(hands: List<Hand>): Int {
    return hands.sorted().mapIndexed { index, hand ->
        hand.bid * (index + 1)
    }.sum()
}

private class Hand(input: String, private val jokerMode: Boolean = false): Comparable<Hand> {
    val hand = input.split(' ')[0]
    val bid = input.split(' ')[1].toInt()
    val type = if(jokerMode) Type.fromWithJokers(hand) else Type.from(hand)

    private fun Char.value(): Int {
        return when(this) {
            'T' -> 10
            'J' -> if(jokerMode) 1 else 11
            'Q' -> 12
            'K' -> 13
            'A' -> 15
            else -> digitToInt()
        }
    }

    override fun compareTo(other: Hand): Int {
        type.sortValue.compareTo(other.type.sortValue).let {
            if(it != 0) return it
        }
        hand.forEachIndexed { index, c ->
            c.value().compareTo(other.hand[index].value()).let {
                if(it != 0) return it
            }
        }
        return 0
    }
}

private enum class Type(val nextWithJoker: Type?, val sortValue: Int) {
    FiveOfAKind(null, 7),
    FourOfAKind(FiveOfAKind, 6),
    FullHouse(FourOfAKind, 5),
    ThreeOfAKind(FourOfAKind, 4),
    TwoPair(FullHouse, 3),
    OnePair(ThreeOfAKind, 2),
    HighCard(OnePair, 1);

    companion object {
        fun from(hand: String): Type {
            val times = hand.groupBy { it }.map { it.value.size }
            return when {
                times.contains(5) -> FiveOfAKind
                times.contains(4) -> FourOfAKind
                times.contains(3) && times.contains(2) -> FullHouse
                times.contains(3) -> ThreeOfAKind
                times.count { it == 2 } == 2 -> TwoPair
                times.contains(2) -> OnePair
                else -> HighCard
            }
        }

        fun fromWithJokers(hand: String): Type {
            if(hand == "JJJJJ") return FiveOfAKind
            var type = from(hand.filter { it != 'J' })
            repeat(hand.count { it == 'J' }) {
                type = type.nextWithJoker ?: throw Exception("FiveOfAKind can't have more jokers")
            }
            return type
        }
    }
}
