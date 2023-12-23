package shared

data class Point(val x: Int, val y: Int) {
    fun up() = Point(x, y - 1)
    fun right() = Point(x + 1, y)
    fun down() = Point(x, y + 1)
    fun left() = Point(x - 1, y)
}
