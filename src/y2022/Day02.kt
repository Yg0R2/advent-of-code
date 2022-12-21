package y2022

import DayX
import y2022.Day02.Results.DRAW
import y2022.Day02.Results.LOOS
import y2022.Day02.Results.WIN
import y2022.Day02.Signals.PAPER
import y2022.Day02.Signals.ROCK
import y2022.Day02.Signals.SCISSORS

class Day02 : DayX<Int>(15, 12) {

    /**
     * A, X => Rock (1 point)
     * B, Y => Paper (2 points)
     * C, Z => Scissors (3 points)
     *
     * Loos: 0 points
     * Draw: 3 points
     * Win: 6 points
     */
    override fun part1(input: List<String>): Int =
        input.sumOf {
            val opponent = Signals.getByShape(it[0])
            val me = Signals.getByShape(it[2])

            val outcome = when (opponent) {
                ROCK -> {
                    when (me) {
                        ROCK -> DRAW
                        PAPER -> WIN
                        SCISSORS -> LOOS
                    }
                }

                PAPER -> {
                    when (me) {
                        ROCK -> LOOS
                        PAPER -> DRAW
                        SCISSORS -> WIN
                    }
                }

                SCISSORS -> {
                    when (me) {
                        ROCK -> WIN
                        PAPER -> LOOS
                        SCISSORS -> DRAW
                    }
                }
            }

            outcome.point + me.point
        }

    /**
     * A => Rock (1 point)
     * B => Paper (2 points)
     * C => Scissors (3 points)
     *
     * X => Loos (0 points)
     * Y => Draw (3 points)
     * Z => Win (6 points)
     */
    override fun part2(input: List<String>): Int =
        input.sumOf {
            val opponent = Signals.getByShape(it[0])
            val outcome = Results.getByOutcome(it[2])

            val me = when (opponent) {
                ROCK -> {
                    when (outcome) {
                        LOOS -> SCISSORS
                        DRAW -> ROCK
                        WIN -> PAPER
                    }
                }

                PAPER -> {
                    when (outcome) {
                        LOOS -> ROCK
                        DRAW -> PAPER
                        WIN -> SCISSORS
                    }
                }

                SCISSORS -> {
                    when (outcome) {
                        LOOS -> PAPER
                        DRAW -> SCISSORS
                        WIN -> ROCK
                    }
                }
            }

            outcome.point + me.point
        }

    private enum class Results(
        val point: Int,
        val outcome: Char
    ) {
        LOOS(0, 'X'),
        DRAW(3, 'Y'),
        WIN(6, 'Z');

        companion object {
            fun getByOutcome(outcome: Char) =
                values().firstOrNull {
                    it.outcome == outcome
                } ?: throw IllegalArgumentException("Not found outcome for $outcome")
        }
    }

    private enum class Signals(
        val point: Int,
        vararg val shapes: Char
    ) {
        ROCK(1, 'A', 'X'),
        PAPER(2, 'B', 'Y'),
        SCISSORS(3, 'C', 'Z');

        companion object {
            fun getByShape(shape: Char): Signals =
                values().firstOrNull {
                    it.shapes.contains(shape)
                } ?: throw IllegalArgumentException("Not found signal for $shape")
        }

    }

}
