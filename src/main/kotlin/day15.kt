import shared.Input

fun main() {
    val input = Input.day(15)
    println(day15A(input))
    println(day15B(input))
}

fun day15A(input: Input): Int {
    return input.text.split(',').sumOf(::hash)
}

fun day15B(input: Input): Int {
    val hashMap = MyHashMap()
    input.text.split(',').forEach {
        if(it.contains('-')) {
            val label = it.dropLast(1)
            hashMap.remove(hash(label), label)
        } else {
            val (label, focalLength) = it.split('=')
            hashMap.insert(hash(label), Lens(label, focalLength.toInt()))
        }
    }
    return hashMap.focusingPower()
}

private fun hash(string: String): Int {
    var value = 0
    for(char in string) {
        value += char.code
        value *= 17
        value %= 256
    }
    return value
}

private class MyHashMap {
    private val boxes = List(256) { mutableListOf<Lens>() }

    fun insert(box: Int, lens: Lens) {
        boxes[box].find { it.label == lens.label }?.let {
            it.focalLength = lens.focalLength
            return
        }
        boxes[box].add(lens)
    }

    fun remove(box: Int, label: String) {
        boxes[box].removeIf { it.label == label }
    }

    fun focusingPower(): Int {
        return boxes.foldIndexed(0) { box, acc, lenses ->
            acc + lenses.foldIndexed(0) { index, boxAcc, lens ->
                boxAcc + (box + 1) * (index + 1) * lens.focalLength
            }
        }
    }
}

private data class Lens(val label: String, var focalLength: Int)