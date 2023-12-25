import shared.*

fun main() {
    val input = Input.day(19)
    println(day19A(input))
    println(day19B(input))
}

fun day19A(input: Input): Int {
    val workflows = Workflow.init(input)
    return input.chunks[1].lines().sumOf { line ->
        val ints = line.toInts()
        val part = mapOf(
            'x' to ints[0],
            'm' to ints[1],
            'a' to ints[2],
            's' to ints[3],
        )
        var workflow = workflows.first { it.name == "in" }
        while (true) {
            val next = workflow.process(part)
            if (next == "R") return@sumOf 0
            if (next == "A") return@sumOf ints.sum()
            workflow = workflows.first { it.name == next }
        }
        throw Exception()
    }
}

fun day19B(input: Input): Long {
    val workflows = Workflow.init(input)

    var parts = listOf<Pair<Part, String?>>(
        Part(1 .. 4000, 1 .. 4000, 1 .. 4000, 1 .. 4000) to "in"
    )
    while (parts.any { it.second != "A" }) {
        parts = parts.filter { it.second != "R" }.flatMap { part ->
            if(part.second == "A") {
                listOf(part)
            } else {
                workflows.first { it.name == part.second }.process(part.first)
            }
        }
    }
    return parts.sumOf {
        it.first.x.count().toLong() *
                it.first.m.count().toLong() *
                it.first.a.count().toLong() *
                it.first.s.count().toLong()
    }
}

private class Workflow(val name: String, val rules: List<Rule>) {
    fun process(part: Map<Char, Int>): String {
        return rules.firstNotNullOf { it.process(part) }
    }

    fun process(part: Part): List<Pair<Part, String?>> {
        var parts = listOf<Pair<Part, String?>>(part to null)
        rules.forEach { rule ->
            parts = parts.flatMap {
                if(it.second == null) {
                    rule.process(it.first)
                } else {
                    listOf(it)
                }
            }
        }
        return parts
    }

    companion object {
        fun init(input: Input): List<Workflow> {
            return input.chunks[0].lines().map { line ->
                val (name, rest) = line.split('{')
                val rules = rest.dropLast(1).split(',').map { ruleString ->
                    Regex("""(\w)(<|>)(\d+):(\w+)""").find(ruleString)?.let {
                        Rule(
                            it.groupValues[1].first(),
                            it.groupValues[2].first(),
                            it.groupValues[3].toInt(),
                            it.groupValues[4],
                        )
                    } ?: Rule(null, null, null, ruleString)
                }
                Workflow(name, rules)
            }
        }
    }
}

private class Rule(val property: Char?, val compare: Char?, val limit: Int?, val to: String) {
    fun process(part: Map<Char, Int>): String? {
        if (property == null || compare == null || limit == null) {
            return to
        }
        return when {
            compare == '>' && part[property]!! > limit -> to
            compare == '<' && part[property]!! < limit -> to
            else -> null
        }
    }

    fun process(part: Part): List<Pair<Part, String?>> {
        if (property == null || compare == null || limit == null) {
            return listOf(part to to)
        }
        return when (property) {
            'x' -> {
                return part.x.split(limit, compare, to).map {
                    Part(it.first, part.m, part.a, part.s) to it.second
                }
            }
            'm' -> {
                return part.m.split(limit, compare, to).map {
                    Part(part.x, it.first, part.a, part.s) to it.second
                }
            }
            'a' -> {
                return part.a.split(limit, compare, to).map {
                    Part(part.x, part.m, it.first, part.s) to it.second
                }
            }
            's' -> {
                return part.s.split(limit, compare, to).map {
                    Part(part.x, part.m, part.a, it.first) to it.second
                }
            }
            else -> emptyList()
        }
    }
}

private class Part(val x: IntRange, val m: IntRange, val a: IntRange, val s: IntRange)

private fun IntRange.split(limit: Int, compare: Char, to: String): List<Pair<IntRange, String?>> {
    if (contains(limit)) {
        if (compare == '<') {
            return listOf(
                first..<limit to to,
                limit..last to null,
            )
        }
        return listOf(
            first..limit to null,
            limit + 1..last to to,
        )
    }
    if (compare == '<') {
        return listOf(
            this to if (last < limit) to else null
        )
    }
    return listOf(
        this to if (first > limit) to else null
    )
}

