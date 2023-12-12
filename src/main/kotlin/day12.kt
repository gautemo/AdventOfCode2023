import shared.Input

/* WARNING temp not working code */

fun main() {
    val input = Input.day(12)
    println(day12A(input))
    println(day12B(input))
}

fun day12A(input: Input): Int {
    return input.lines.sumOf {
        springsArrangements(it)
    }
}

fun day12B(input: Input): Long {
    var count = 1
    return input.lines.sumOf {
        println("calculating $count of ${input.lines.size}")
        count++
        val (first, second) = it.split(' ')
        val firstValue = springsArrangements(it)
        val secondValue = springsArrangements(List(2) { first }.joinToString("?") + " " + List(2){ second }.joinToString(","))
        val multiply = secondValue / firstValue
        var sum = firstValue.toLong()
        for(i in 1..4) {
            if(i == 3) {
                val checkpoint = springsArrangements(List(3) { first }.joinToString("?") + " " + List(3){ second }.joinToString(",")).toLong()
                if(checkpoint != sum) {
                    sum = springsArrangements(List(5) { first }.joinToString("?") + " " + List(5){ second }.joinToString(",")).toLong()
                    break
                }
            }
            sum *= multiply
        }
        sum
    }
}
// 4195562038729 too low

fun springsArrangements(springs: String): Int {
    val permutations = permuteSprings(springs)
    return permutations.count {
        verifySprings(it)
    }
}

private fun permuteSprings(springs: String): List<String> {
    if(!verifyStartOfSprings(springs)) {
        return emptyList()
    }
    if(!springs.contains('?')) {
        return listOf(springs)
    }
    return permuteSprings(springs.replaceFirst('?', '.')) + permuteSprings(springs.replaceFirst('?', '#'))
}

private fun verifySprings(springs: String): Boolean {
    val knownPattern = getPattern(springs)
    val pattern = springs.split(' ')[1]
    return pattern == knownPattern
}

private fun verifyStartOfSprings(springs: String): Boolean {
    var start = springs.substringBefore("?")
    while (start.endsWith('#')) {
        start = start.removeSuffix("#")
    }
    return springs.split(' ')[1].startsWith(getPattern(start))
}

private fun getPattern(springs: String): String {
    return Regex("""#+""")
        .findAll(springs)
        .map { it.value.length }
        .joinToString(",")
}