import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {
        val rope = Rope(2)

        input.initSteps()
            .forEach { rope.move(it.first, it.second) }

        return rope.tailCoordinates.size
    }

    fun part2(input: List<String>): Int {
        val rope = Rope(10)

        input.initSteps()
            .forEach { rope.move(it.first, it.second) }

        return rope.tailCoordinates.size
    }

    // test if implementation meets criteria from the description, like:
    with("Day09") {
        val testInput = readInput("${this}_test")
        checkResult("test part1", 13) {
            part1(testInput)
        }
        checkResult("test part2", 1) {
            part2(testInput)
        }
        checkResult("test2 part2", 36) {
            part2(readInput("${this}_test2"))
        }

        val input = readInput(this)
        println(part1(input))
        println(part2(input))
    }

}

private data class Knot(
    val x: Int,
    val y: Int
)

private enum class Movements {
    L, R, U, D
}

private class Rope(
    length: Int
) {

    private val knots: MutableList<Knot> = MutableList(length) {
        Knot(0, 0)
    }

    val tailCoordinates: MutableSet<Knot> = mutableSetOf<Knot>()
        .also { it.add(knots[knots.size - 1]) }

    fun move(movement: Movements, amount: Int) {
        IntRange(1, amount).forEach { _ ->
            moveHead(movement)

            moveTail()

            tailCoordinates.add(knots[knots.size - 1])
        }
    }

    private fun getTailDirection(diff: Int) =
        if (diff == 0) {
            0
        } else if (diff < 0) {
            1
        } else {
            -1
        }

    private fun moveHead(movement: Movements) {
        knots[0] = with(knots[0]) {
            when (movement) {
                Movements.L -> {
                    copy(x = x - 1)
                }

                Movements.R -> {
                    copy(x = x + 1)
                }

                Movements.U -> {
                    copy(y = y - 1)
                }

                Movements.D -> {
                    copy(y = y + 1)
                }
            }
        }
    }

    private fun moveTail() {
        IntRange(1, knots.size - 1).forEach {
            if (tailHasToMove(it)) {
                knots[it] = with(knots[it]) {
                    copy(
                        x = x + getTailDirection(x - knots[it - 1].x),
                        y = y + getTailDirection(y - knots[it - 1].y)
                    )
                }
            }
        }
    }

    private fun tailHasToMove(currentIndex: Int): Boolean =
        (abs(knots[currentIndex].x - knots[currentIndex - 1].x) == 2) ||
                (abs(knots[currentIndex].y - knots[currentIndex - 1].y) == 2)

}

private fun List<String>.initSteps(): List<Pair<Movements, Int>> =
    map { Movements.valueOf(it[0].toString()) to it.substring(2).toInt() }
