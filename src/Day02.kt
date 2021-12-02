import Directions.DOWN
import Directions.FORWARD
import Directions.UP

fun main() {
    fun part1(input: List<String>): Int {
        var depth = 0
        var horizontal = 0

        input.toDirectionAmountPairList()
            .forEach { (direction, amount) ->
                when (direction) {
                    DOWN -> depth += amount
                    FORWARD -> horizontal += amount
                    UP -> depth -= amount
                }
            }

        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        var depth = 0
        var horizontal = 0
        var aim = 0

        input.toDirectionAmountPairList()
            .forEach { (direction, amount) ->
                when (direction) {
                    DOWN -> aim += amount
                    FORWARD -> {
                        horizontal += amount
                        depth += aim * amount
                    }
                    UP -> aim -= amount
                }
            }

        return horizontal * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))

}

private fun List<String>.toDirectionAmountPairList(): List<Pair<Directions, Int>> = this
    .filter { it.isNotBlank() }
    .map { it.split(" ") }
    .map { (directionString, amountString) -> Pair(directionString.toDirection(),  amountString.toInt()) }

private fun String.toDirection(): Directions = Directions.of(this)

private enum class Directions(val value: String) {

    DOWN("down"),
    FORWARD("forward"),
    UP("up");

    companion object {
        fun of(value: String): Directions = values()
            .find { it.value == value }
            ?: throw IllegalArgumentException("invalid direction: $value")
    }
}
