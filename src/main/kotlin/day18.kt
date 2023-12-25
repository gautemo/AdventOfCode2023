import shared.*

/* WARNING not cleaned up yet */

fun main() {
    val input = Input.day(18)
    println(day18A(input))
    println(day18B(input))
}

fun day18A(input: Input): Long {
    var mover = Mover(Point(0, 0), Right)
    val path = mutableSetOf(mover)
    input.lines.forEach {
        val (direction, meters) = it.split(' ')
        if(direction == "U") {
            path.last().let { r ->
                path.remove(r)
                path.add(Mover(r.at, Up))
            }
        }
        if(direction == "D") {
            path.last().let { r ->
                path.remove(r)
                path.add(Mover(r.at, Down))
            }
        }
        repeat(meters.toInt()) {
            mover = when(direction) {
                "U" -> mover.up()
                "R" -> mover.right()
                "D" -> mover.down()
                "L" -> mover.left()
                else -> throw Exception()
            }
            path.add(mover)
        }
    }
    var paint = ""
    val sumOnY = mutableMapOf<Int, Long>()
    //var sum = 0
    for(y in path.minOf { it.at.y } .. path.maxOf { it.at.y }) {
        for(x in path.minOf { it.at.x } .. path.maxOf { it.at.x }) {
            if(path.any { it.at == Point(x, y) }) {
                //sum++
                sumOnY[y] = (sumOnY[y] ?: 0) + 1
                paint += if(path.first { it.at == Point(x, y) }.dir == Up) "U" else "#"
            } else {
                val leftWall = path.filter {
                    it.at.y == y && it.at.x < x && (it.dir == Up || it.dir == Down)
                }.maxByOrNull { it.at.x }
                val rightWall = path.filter {
                    it.at.y == y && it.at.x > x && (it.dir == Up || it.dir == Down)
                }.minByOrNull { it.at.x }
                val isInside = leftWall?.dir == Up && rightWall?.dir == Down
                if(isInside) {
                    //sum++
                    sumOnY[y] = (sumOnY[y] ?: 0) + 1
                    paint += "o"
                } else {
                    paint += "."
                }
            }
        }
        paint += "\n"
    }
    //println(paint)
    //return sum*/
    var on = Point(0, 0)
    val vectors = input.lines.map { line ->
        val (direction, meters) = line.split(' ')
        when(direction) {
            "R" -> Vector(on, Point(on.x + meters.toInt(), on.y))
            "D" -> Vector(on, Point(on.x, on.y + meters.toInt()))
            "L" -> Vector(on, Point(on.x - meters.toInt(), on.y))
            "U" -> Vector(on, Point(on.x, on.y - meters.toInt()))
            else -> throw Exception()
        }.also {
            on = it.b
        }
    }
    var sum = 0L
    val minY = vectors.minOf { minOf(it.a.y, it.b.y) }
    val maxY = vectors.maxOf { maxOf(it.a.y, it.b.y) }
    for(y in minY .. maxY) {
        println("$y - ${paint.lines()[y - minY]}")
        val walls = vectors.filter {
            it.a.y != it.b.y && y >= minOf(it.a.y, it.b.y) && y <= maxOf(it.a.y, it.b.y)
        }.sortedBy { it.a.x }
        var lineSum = 0L
        val space = walls.zipWithNext { a, b ->
            val aDirUp = a.a.y > a.b.y
            val bDirUp = b.a.y > b.b.y
            val isHorizontallyConnected = vectors.any {
                it.a.y == y &&
                        ((it.a.x == a.a.x && it.b.x == b.a.x) ||
                                (it.b.x == a.a.x && it.a.x == b.a.x))
            }
            if((aDirUp && !bDirUp) || isHorizontallyConnected) {
                a.a.x to b.a.x
            } else {
                null
            }
        }.filterNotNull()
        sum += space.sumOf {
            (it.second - it.first) + 1
        } - space.count { a -> space.any { b -> a.first == b.second } }
        lineSum += space.sumOf {
            (it.second - it.first) + 1
        } - space.count { a -> space.any { b -> a.first == b.second } }
        /*walls.zipWithNext { a, b ->
            val aDirUp = a.a.y > a.b.y
            val bDirUp = b.a.y > b.b.y
            val isHorizontallyConnected = vectors.any {
                it.a.y == y &&
                        ((it.a.x == a.a.x && it.b.x == b.a.x) ||
                                (it.b.x == a.a.x && it.a.x == b.a.x))
            }
            if (aDirUp && !bDirUp) {
                sum += (b.a.x - a.a.x) + 1
                lineSum += (b.a.x - a.a.x) + 1
            } else if(isHorizontallyConnected) {
                sum += (b.a.x - a.a.x)
                lineSum += (b.a.x - a.a.x)
            }
        }*/
        if(lineSum != sumOnY[y]) {
            println("verify ^")
        }
    }
    return sum
}

fun day18B(input: Input): Long {
    var on = Point(0, 0)
    val vectors = input.lines.map { line ->
        val hexa = Regex("""\(#((\w|\d)+)\)""").find(line)!!.groupValues[1]
        val meters = hexa.dropLast(1).toInt(radix = 16)
        when(hexa.last()) {
            '0' -> Vector(on, Point(on.x + meters, on.y))
            '1' -> Vector(on, Point(on.x, on.y + meters))
            '2' -> Vector(on, Point(on.x - meters, on.y))
            '3' -> Vector(on, Point(on.x, on.y - meters))
            else -> throw Exception()
        }.also {
            on = it.b
        }
    }
    var sum = 0L
    val minY = vectors.minOf { minOf(it.a.y, it.b.y) }
    val maxY = vectors.maxOf { maxOf(it.a.y, it.b.y) }
    for(y in minY .. maxY) {
        val walls = vectors.filter {
            it.a.y != it.b.y && y >= minOf(it.a.y, it.b.y) && y <= maxOf(it.a.y, it.b.y)
        }.sortedBy { it.a.x }
        val space = walls.zipWithNext { a, b ->
            val aDirUp = a.a.y > a.b.y
            val bDirUp = b.a.y > b.b.y
            val isHorizontallyConnected = vectors.any {
                it.a.y == y &&
                        ((it.a.x == a.a.x && it.b.x == b.a.x) ||
                                (it.b.x == a.a.x && it.a.x == b.a.x))
            }
            if((aDirUp && !bDirUp) || isHorizontallyConnected) {
                a.a.x to b.a.x
            } else {
                null
            }
        }.filterNotNull()
        sum += space.sumOf {
            (it.second - it.first) + 1
        } - space.count { a -> space.any { b -> a.first == b.second } }
    }
    return sum
}

private class Vector(val a: Point, val b: Point)
