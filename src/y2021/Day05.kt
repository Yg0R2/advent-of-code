package y2021

import kotlin.math.abs
import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .toOceanFloor { x1, y1, x2, y2, oceanFloor ->
                if (x1 == x2) {
                    val yDelta = y2.compareTo(y1)

                    for (y in 0..abs(y1 - y2)) {
                        oceanFloor[y1 + y * yDelta][x1] += 1
                    }
                }

                if (y1 == y2) {
                    val xDelta = x2.compareTo(x1)

                    for (x in 0..abs(x1 - x2)) {
                        oceanFloor[y1][x1 + x * xDelta] += 1
                    }
                }
            }
            .countOverlaps()
    }

    fun part2(input: List<String>): Int {
        return input
            .toOceanFloor { x1, y1, x2, y2, oceanFloor ->
                val xDelta = x2.compareTo(x1)
                val yDelta = y2.compareTo(y1)

                for (index in 0..max(abs(x1 - x2), abs(y1 - y2))) {
                    oceanFloor[y1 + index * yDelta][x1 + index * xDelta] += 1
                }
            }
            .countOverlaps()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

private fun Array<Array<Int>>.countOverlaps() =
    this.flatten()
        .filter { it >= 2 }
        .size

private fun List<String>.toOceanFloor(block: (x1: Int, y1: Int, x2: Int, y2: Int, oceanFloor: Array<Array<Int>>) -> Unit): Array<Array<Int>> {
    val oceanFloor = Array(1000) {
        Array(1000) { 0 }
    }

    this.map { coordinatePairs ->
            coordinatePairs.split("->")
                .map { it.trim() }
        }
        .map { coordinatePair ->
            coordinatePair.map { it.split(",").toIntList() }
        }
        .forEach {
            block(it[0][0], it[0][1], it[1][0], it[1][1], oceanFloor)
        }

    return oceanFloor
}
