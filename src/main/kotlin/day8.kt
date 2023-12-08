import shared.*

fun main() {
    val input = Input.day(8)
    println(day8A(input))
    println(day8B(input))
}

fun day8A(input: Input): Long {
    val (instructions, network) = input.chunks
    val nodes = network.lines().toNetwork()
    val ghost = Ghost(nodes.first { it.name == "AAA" })
    while (ghost.on.name != "ZZZ") {
        ghost.move(instructions[(ghost.step % instructions.length).toInt()])
    }
    return ghost.step
}

/* Luckily the input is rigged so that the distance to the first end, is the length of the repeating cycle */
fun day8B(input: Input): Long {
    val (instructions, network) = input.chunks
    val nodes = network.lines().toNetwork()
    val ghosts = nodes.filter { it.name.endsWith('A') }.map { Ghost(it) }
    ghosts.forEach { ghost ->
        while (!ghost.on.name.endsWith('Z')) {
            ghost.move(instructions[(ghost.step % instructions.length).toInt()])
        }
    }
    return ghosts.fold(1){ acc, g -> lcm(acc, g.step) }
}

private fun List<String>.toNetwork(): List<Node> {
    val nodes = map { Node(it.split(' ')[0]) }
    forEach { line ->
        val node = nodes.first { it.name == line.split(' ')[0]}
        val leftName = Regex("""\((\w+),""").find(line)!!.groupValues[1]
        val rightName = Regex(""",\s(\w+)\)""").find(line)!!.groupValues[1]
        node.left = nodes.first { it.name == leftName }
        node.right = nodes.first { it.name == rightName }
    }
    return nodes
}

private class Node(val name: String){
    lateinit var left: Node
    lateinit var right: Node
}

private class Ghost(var on: Node) {
    var step = 0L

    fun move(to: Char) {
        when(to) {
            'L' -> on = on.left
            'R' -> on = on.right
        }
        step++
    }
}