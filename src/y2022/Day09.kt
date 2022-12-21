package y2022

import DayX
import y2022.Day09.Direction.D
import y2022.Day09.Direction.L
import y2022.Day09.Direction.R
import y2022.Day09.Direction.U
import kotlin.math.abs

class Day09 : DayX<Int>(listOf(13), listOf(1, 36)) {

    override fun part1(input: List<String>): Int {
        val rope = Rope(2)

        input.initSteps()
            .forEach { rope.move(it.first, it.second) }

        return rope.tailCoordinates.size
    }

    override fun part2(input: List<String>): Int {
        val rope = Rope(10)

        input.initSteps()
            .forEach { rope.move(it.first, it.second) }

        return rope.tailCoordinates.size
    }

    companion object {
        private fun List<String>.initSteps(): List<Pair<Direction, Int>> =
            map { Direction.valueOf(it[0].toString()) to it.substring(2).toInt() }
    }

    private data class Knot(
        val x: Int,
        val y: Int
    )

    private enum class Direction {
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

        fun move(direction: Direction, amount: Int) {
            IntRange(1, amount).forEach { _ ->
                moveHead(direction)

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

        private fun moveHead(direction: Direction) {
            knots[0] = with(knots[0]) {
                when (direction) {
                    L -> {
                        copy(x = x - 1)
                    }

                    R -> {
                        copy(x = x + 1)
                    }

                    U -> {
                        copy(y = y - 1)
                    }

                    D -> {
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

}
