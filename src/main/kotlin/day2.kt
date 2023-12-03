import shared.Input

fun main() {
    val input = Input.day(2)
    println(day2A(input))
    println(day2B(input))
}

fun day2A(input: Input): Int {
    return input.lines.sumOf {
        val game = CubeGame(it)
        if (game.maxRed <= 12 && game.maxGreen <= 13 && game.maxBlue <= 14) {
            game.id
        } else {
            0
        }
    }
}

fun day2B(input: Input): Int {
    return input.lines.sumOf {
        val game = CubeGame(it)
        game.maxRed * game.maxGreen * game.maxBlue
    }
}

private class CubeGame(game: String) {
    val id = game.split(':')[0].split(' ')[1].toInt()
    val maxRed = Regex("""(\d+)\sred""").findAll(game).maxOf { m -> m.groupValues[1].toInt() }
    val maxGreen = Regex("""(\d+)\sgreen""").findAll(game).maxOf { m -> m.groupValues[1].toInt() }
    val maxBlue = Regex("""(\d+)\sblue""").findAll(game).maxOf { m -> m.groupValues[1].toInt() }
}