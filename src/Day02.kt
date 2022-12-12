import Results.DRAW
import Results.LOOS
import Results.WIN
import Signals.PAPER
import Signals.ROCK
import Signals.SCISSORS

enum class Results(
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

enum class Signals(
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

fun main() {

    /**
     * A, X => Rock (1 point)
     * B, Y => Paper (2 points)
     * C, Z => Scissors (3 points)
     *
     * Loos: 0 points
     * Draw: 3 points
     * Win: 6 points
     */
    fun part1(input: List<String>): Int {
        return input.sumOf {
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
    fun part2(input: List<String>): Int {
        return input.sumOf {
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
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    checkResult("test part1", 15) {
        part1(testInput)
    }
    checkResult("test part2", 12) {
        part2(testInput)
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))

}
