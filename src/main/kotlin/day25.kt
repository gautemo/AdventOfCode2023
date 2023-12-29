import shared.*

fun main() {
    val input = Input.day(25)
    println(day25A(input))
}

fun day25A(input: Input): Int {
    val pairs = sortedBasedOnConnections(toPairs(input.lines))
    for (cut1 in 0..10) {
        for (cut2 in cut1..<10) {
            for (cut3 in cut2..<10) {
                if (cut1 == cut3 || cut2 == cut3) continue
                getGroups(pairs.filterIndexed { index, _ -> !listOf(cut1, cut2, cut3).contains(index) }).let {
                    if (it.size == 2) {
                        return it[0].size * it[1].size
                    }
                }
            }
        }
    }
    throw Exception()
}

private fun toPairs(lines: List<String>): List<Pair<String, String>> {
    val pairs = mutableListOf<Pair<String, String>>()
    lines.forEach { line ->
        val left = line.split(':')[0]
        val rights = line.split(':')[1].trim().split(' ')
        rights.forEach { right ->
            if (pairs.none { it == left to right || it == right to left }) {
                pairs.add(left to right)
            }
        }
    }
    return pairs
}

private fun getGroups(pairs: List<Pair<String, String>>): List<Set<String>> {
    val groups = mutableListOf<MutableSet<String>>()
    pairs.forEach { pair ->
        val existingGroups = groups.filter { it.contains(pair.first) || it.contains(pair.second) }
        if (existingGroups.isEmpty()) {
            groups.add(mutableSetOf(pair.first, pair.second))
        } else if (existingGroups.size == 1) {
            existingGroups[0].add(pair.first)
            existingGroups[0].add(pair.second)
        } else {
            existingGroups.drop(1).forEach {
                existingGroups[0].addAll(it)
                groups.remove(it)
            }
        }
    }
    return groups
}

private fun sortedBasedOnConnections(pairs: List<Pair<String, String>>): List<Pair<String, String>> {
    val popular = pairs.associateWith { 0 }.toMutableMap()
    val nodes = pairs.flatMap { listOf(it.first, it.second) }.toSet().toMutableList()
    nodes.forEachIndexed { index, a ->
        println("$index of ${nodes.size}")
        val prev = dijkstra(pairs, a)
        nodes.drop(index + 1).forEach { b ->
            pathBetweenNodes(pairs, prev, b).forEach {
                popular[it] = popular[it]!! + 1
            }
        }
    }
    return pairs.sortedByDescending { pair ->
        popular[pair]
    }
}

private fun dijkstra(pairs: List<Pair<String, String>>, source: String): Map<String, String?> {
    val queue = pairs.flatMap { listOf(it.first, it.second) }.toSet().toMutableList()
    val dist = queue.associateWith { Int.MAX_VALUE }.toMutableMap()
    val prev = mutableMapOf<String, String?>()
    dist[source] = 0
    while (queue.isNotEmpty()) {
        val u = queue.minBy { dist[it] ?: 0 }.also { queue.remove(it) }
        pairs
            .filter { it.first == u || it.second == u }
            .forEach {
                val v = if (it.first == u) it.second else it.first
                val alt = dist[u]!! + 1
                if (alt < dist[v]!!) {
                    dist[v] = alt
                    prev[v] = u
                }
            }
    }
    return prev
}

private fun pathBetweenNodes(
    pairs: List<Pair<String, String>>,
    prev: Map<String, String?>,
    target: String
): List<Pair<String, String>> {
    val path = mutableListOf<Pair<String, String>>()
    var last = target
    while (prev[last] != null) {
        path.add(pairs.first {
            (it.first == last && it.second == prev[last]) || (it.second == last && it.first == prev[last])
        })
        last = prev[last]!!
    }
    return path
}