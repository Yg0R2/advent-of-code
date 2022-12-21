package y2021

import y2021.Directions.DOWN
import y2021.Directions.FORWARD
import y2021.Directions.UP

fun main() {
    fun part1(input: List<String>): Int {
        return input.toDirectionAmountPairList()
            .fold(Submarine(0, 0, 0)) { acc, (direction, amount) ->
                when (direction) {
                    DOWN -> acc.apply { depth += amount }
                    FORWARD -> acc.apply { horizontal += amount }
                    UP -> acc.apply { depth -= amount }
                }
            }
            .let { it.horizontal * it.depth}
    }

    fun part2(input: List<String>): Int {
        return input.toDirectionAmountPairList()
            .fold(Submarine(0, 0, 0)) { acc, (direction, amount) ->
                when (direction) {
                    DOWN -> acc.apply { aim += amount }
                    FORWARD -> acc.apply {
                        horizontal += amount
                        depth += aim * amount
                    }
                    UP -> acc.apply { aim -= amount }
                }
            }
            .let { it.horizontal * it.depth }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))

}

private data class Submarine(
    var aim: Int,
    var depth: Int,
    var horizontal: Int
)

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
