import shared.Input
import shared.toInts

/* WARNING temp not working code */

fun main() {
    val input = Input.day(12)
    // println(day12A(input))
    println(day12B(input))
}

fun day12A(input: Input): Long {
    return input.lines.sumOf {
        val (base, basePattern) = it.split(' ')
        val springs = Springs(base, basePattern)
        springs.count()
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
    /*return input.lines.sumOf {
        val (base, basePattern) = it.split(' ')
        val springs = Springs(base, basePattern, 5)
        springs.count()
    }*/
}
// 4195562038729 too low

fun springsArrangements(springs: String): Int {
    val permutations = permuteSprings(springs)
    return permutations.count {
        verifySprings(it)
    }
}

private fun permuteSprings(springs: String): List<String> {
    if(!verifyStartOfSprings(springs) || !verifyGroups(springs) || !verifyMax(springs)) {
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

private fun verifyGroups(springs: String): Boolean {
    val (values, pattern) = springs.split(' ')
    val canCheck = Regex("""(.*)(?:\.#+\?)""").find(values)?.groups?.get(1)?.value ?: return true
    val groups = Regex("""#+""")
        .findAll(canCheck)
        .map { it.value.length }.toList()
    val patternInts = pattern.toInts()
    return groups.size <= patternInts.size
}

private fun verifyMax(springs: String): Boolean {
    val (values, pattern) = springs.split(' ')
    val groups = Regex("""#+""")
        .findAll(values)
        .map { it.value.length }.toList()
    val patternInts = pattern.toInts()
    return groups.max() <= patternInts.max()
}

private fun getPattern(springs: String): String {
    return Regex("""#+""")
        .findAll(springs)
        .map { it.value.length }
        .joinToString(",")
}

class Springs(base: String, basePattern: String, levels: Int = 1) {
    val basePermutations = permute(base.removePrefixes('.').removeSuffixes('.'))
    var permutes = basePermutations
    val pattern = List(levels) { basePattern }.joinToString(",")

    init {
        repeat(levels - 1) {
            permutes = permutes.flatMap { p ->
                basePermutations.flatMap { bP ->
                    listOf("$p.$bP", "$p#$bP").filter {
                        groups(it).size <= pattern.toInts().size &&
                                groups(it).max() <= pattern.toInts().max()
                    }
                }
            }
        }
    }

    private fun permute(values: String): List<String> {
        if(!values.contains('?')) {
            return listOf(values)
        }
        return permute(values.replaceFirst('?', '.')) + permute(values.replaceFirst('?', '#'))
    }

    private fun verify(values: String): Boolean {
        return pattern == groups(values).joinToString(",")
    }

    private fun groups(values: String): List<Int> {
        return Regex("""#+""")
            .findAll(values)
            .map { it.value.length }
            .toList()
    }

    fun count() = permutes.count { verify(it) }.toLong()
}

fun String.removePrefixes(char: Char): String {
    var transformed = this
    while (this.startsWith(char)) {
        transformed = transformed.removePrefix(char.toString())
    }
    return transformed
}

fun String.removeSuffixes(char: Char): String {
    var transformed = this
    while (this.endsWith(char)) {
        transformed = transformed.removeSuffix(char.toString())
    }
    return transformed
}