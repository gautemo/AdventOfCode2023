import shared.*

/* WARNING not cleaned up yet */

fun main() {
    val input = Input.day(8)
    println(day8A(input))
    println(day8B(input))
}

fun day8A(input: Input): Int {
    val (instructions, network) = input.chunks
    val nodes = network.lines().map { Node(it.split(' ')[0], null, null) }
    network.lines().forEach { line ->
        val node = nodes.first { it.name == line.split(' ')[0]}
        val leftName = Regex("""\((\w+),""").find(line)!!.groupValues[1]
        val rightName = Regex(""",\s(\w+)\)""").find(line)!!.groupValues[1]
        node.left = nodes.first { it.name == leftName }
        node.right = nodes.first { it.name == rightName }
    }
    var count = 0
    var on = nodes.first { it.name == "AAA" }
    while (on.name != "ZZZ") {
        when(instructions[count % instructions.length]) {
            'L' -> on = on.left!!
            'R' -> on = on.right!!
        }
        count++
    }
    return count
}

fun day8B(input: Input): Long {
    val (instructions, network) = input.chunks
    val nodes = network.lines().map { Node(it.split(' ')[0], null, null) }
    network.lines().forEach { line ->
        val node = nodes.first { it.name == line.split(' ')[0]}
        val leftName = Regex("""\((\w+),""").find(line)!!.groupValues[1]
        val rightName = Regex(""",\s(\w+)\)""").find(line)!!.groupValues[1]
        node.left = nodes.first { it.name == leftName }
        node.right = nodes.first { it.name == rightName }
    }
    val ghosts = nodes.filter { it.name.endsWith('A') }.map { Ghost(it, instructions) }
    ghosts.forEach {
        while (!it.stopped) {
            it.move()
        }
    }
    var lcms = mutableListOf(1L)
    ghosts.forEach {
        lcms = it.goals().flatMap { g -> lcms.map { l -> lcm(l, g.toLong()) } }.toMutableList()
    }
    return lcms.min()
}

private class Node(val name: String, var left: Node?, var right: Node?)
private class Ghost(var on: Node, private val instructions: String) {
    var step = 0
        set(value){
            field = if(value == instructions.length) 0 else value
        }
    val trail = mutableListOf(on.name to step)
    var stopped = false

    fun move() {
        when(instructions[step]) {
            'L' -> on = on.left!!
            'R' -> on = on.right!!
        }
        step++
        if(trail.contains(on.name to step)) stopped = true
        trail.add(on.name to step)
    }

    fun goals() = trail.mapIndexedNotNull { index, pair -> if(pair.first.endsWith('Z')) index else null }
}