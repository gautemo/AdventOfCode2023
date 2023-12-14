import shared.Input
import shared.toInts

fun main() {
    val input = Input.day(12)
    println(day12A(input))
    println(day12B(input))
}

fun day12A(input: Input): Long {
    return input.lines.sumOf {
        countPermutes(it)
    }
}

fun day12B(input: Input): Long {
    return input.lines.sumOf {
        val (first, second) = it.split(' ')
        countPermutes("${List(5) { first }.joinToString("?")} ${List(5) { second }.joinToString(",")}")
    }
}

private val cache = mutableMapOf<SpringsState, Long>()

fun countPermutes(springs: String): Long {
    cache.clear()
    val (values, pattern) = springs.split(' ')
    return validPermutes(SpringsState(emptyList(), values), pattern.toInts())
}

private fun validPermutes(springsState: SpringsState, pattern: List<Int>): Long {
    cache[springsState]?.let { return it }
    if(!pattern.joinToString(",").startsWith(springsState.groups.joinToString(","))) {
        return 0
    }
    if(springsState.rest.isEmpty()) {
        if(springsState.groups == pattern) return 1
        return 0
    }
    if(!springsState.rest.contains('?')){
        val answer = springsState.groups + getGroups(springsState.rest)
        if(answer == pattern) return 1
        return 0
    }
    val permutes1 = validPermutes(springsState.toNext('.'), pattern)
    val permutes2 = validPermutes(springsState.toNext('#'), pattern)
    cache[springsState] = permutes1 + permutes2
    return permutes1 + permutes2
}

private data class SpringsState(val groups: List<Int>, val rest: String) {
    fun toNext(to: Char): SpringsState {
        val changed = rest.replaceFirst('?', to)
        if(!changed.contains('?')) {
            return SpringsState(groups, changed)
        }
        var knownSection = changed.substringBefore("?")
        while (knownSection.endsWith('#')) {
            knownSection = knownSection.removeSuffix("#")
        }
        val toRest = changed.removePrefix(knownSection)
        val groupList = getGroups(knownSection)
        return SpringsState(groups + groupList, toRest)
    }
}

private fun getGroups(values: String): List<Int> {
    return Regex("""#+""")
        .findAll(values)
        .map { it.value.length }.toList()
}

