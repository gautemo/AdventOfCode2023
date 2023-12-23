package shared

data class Point(val x: Int, val y: Int)

sealed interface Direction
data object Up : Direction
data object Right : Direction
data object Down : Direction
data object Left : Direction

data class Mover(val at: Point, val dir: Direction, var steps: Int? = null) {
    fun up() = Mover(Point(at.x, at.y - 1), Up, steps?.plus(1))
    fun right() = Mover(Point(at.x + 1, at.y), Right, steps?.plus(1))
    fun down() = Mover(Point(at.x, at.y + 1), Down, steps?.plus(1))
    fun left() = Mover(Point(at.x - 1, at.y), Left, steps?.plus(1))
}