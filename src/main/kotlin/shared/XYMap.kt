package shared

// inspired of https://github.com/gautemo/AdventOfCode2021/blob/main/src/main/kotlin/shared/XYMap.kt
class XYMap<T>(input: Input, toType: (char: Char) -> T?) {
    private val xyMap = mutableMapOf<Point, T>()

    init {
        for ((y, row) in input.lines.withIndex()) {
            for ((x, char) in row.withIndex()) {
                toType(char)?.let {
                    xyMap[Point(x, y)] = it
                }
            }
        }
    }

    operator fun get(point: Point): T {
        return xyMap[point] ?: throw Exception("Missing")
    }

    fun all(predicate: ((value: T) -> Boolean)? = null): Map<Point, T> {
        if (predicate != null) return xyMap.filter { predicate(it.value) }
        return xyMap
    }

    fun adjacents(
        point: Point,
        includeDiagonal: Boolean = false,
        predicate: ((value: T) -> Boolean)? = null
    ): Map<Point, T> {
        val x = point.x
        val y = point.y
        val adjacentsPoints = listOfNotNull(
            Point(x - 1, y),
            Point(x, y - 1),
            Point(x, y + 1),
            Point(x + 1, y),
            if (includeDiagonal) Point(x - 1, y - 1) else null,
            if (includeDiagonal) Point(x - 1, y + 1) else null,
            if (includeDiagonal) Point(x + 1, y - 1) else null,
            if (includeDiagonal) Point(x + 1, y + 1) else null,
        )
        return xyMap
            .filter { adjacentsPoints.contains(it.key) }
            .let { map ->
                if(predicate != null) {
                    map.filter { predicate(it.value) }
                } else {
                    map
                }
            }
    }
}