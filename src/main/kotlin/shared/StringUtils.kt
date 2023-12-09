package shared

fun String.toInts() = Regex("""-?\d+""").findAll(this).map { it.value.toInt() }.toList()
fun String.toLongs() = Regex("""-?\d+""").findAll(this).map { it.value.toLong() }.toList()