package shared

fun String.toInts() = Regex("""\d+""").findAll(this).map { it.value.toInt() }.toList()