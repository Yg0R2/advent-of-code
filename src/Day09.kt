import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {
        val rope = Rope()

        input.initSteps()
            .forEach { rope.move(it.first, it.second) }

        return rope.tailCoordinates.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    with("Day09") {
        val testInput = readInput("${this}_test")
        checkResult("test part1", 13) {
            part1(testInput)
        }
//        checkResult("test part2", 1) {
//            part2(testInput)
//        }

        val input = readInput(this)
        println(part1(input))
//        println(part2(input))
    }

}

private enum class Movements {
    L, R, U, D
}

private class Rope {

    // x, y
    private var head: Pair<Int, Int> = Pair(0, 0)

    private var tail: Pair<Int, Int> = Pair(0, 0)

    val tailCoordinates: MutableSet<Pair<Int, Int>> = mutableSetOf<Pair<Int, Int>>()
        .also { it.add(tail) }

    fun move(movement: Movements, amount: Int) {
        IntRange(1, amount).forEach { _ ->
            head = head.move(movement)

            if (tailHasToMove()) {
                tail = tail.copy(
                    first = tail.first + getMovementDirection(tail.first - head.first),
                    second = tail.second + getMovementDirection (tail.second - head.second)
                )

                tailCoordinates.add(tail)
            }
        }
    }

    private fun getMovementDirection(diff: Int) =
        if (diff == 0) {
            0
        } else if (diff < 0) {
            1
        } else {
            -1
        }

    private fun Pair<Int, Int>.move(movement: Movements): Pair<Int, Int> =
        when (movement) {
            Movements.L -> {
                copy(first = first - 1)
            }

            Movements.R -> {
                copy(first = first + 1)
            }

            Movements.U -> {
                copy(second = second - 1)
            }

            Movements.D -> {
                copy(second = second + 1)
            }
        }

    private fun tailHasToMove(): Boolean =
        (abs(tail.first - head.first) == 2) ||
                (abs(tail.second - head.second) == 2)

}

private fun List<String>.initSteps(): List<Pair<Movements, Int>> =
    map { Movements.valueOf(it[0].toString()) to it.substring(2).toInt() }
