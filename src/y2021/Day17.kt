package y2021

import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        val targetArea = input[0].getTargetArea()

        var maxHeight = 0
        for (x in 0 until targetArea.second.first) {
            for (y in targetArea.second.second until targetArea.second.second * -1) {
                val initialVelocity = x to y

                var position = 0 to 0
                var maxHeightInTry = 0

                var stepCounter = -1
                while ((position.first <= targetArea.second.first) && (position.second >= targetArea.second.second)) {
                    stepCounter += 1
                    val newVelocity = initialVelocity.calculateTrajectory(stepCounter)

                    position = position.first + newVelocity.first to position.second + newVelocity.second

                    maxHeightInTry = max(maxHeightInTry, position.second)

                    if (targetArea.targetHit(position)) {
                        maxHeight = max(maxHeight, maxHeightInTry)
                        break
                    }
                }
            }
        }

        return maxHeight
    }

    fun part2(input: List<String>): Int {
        val targetArea = input[0].getTargetArea()

        var targetHitCount = 0
        for (x in 0 until targetArea.second.first + 1) {
            for (y in targetArea.second.second until (targetArea.second.second * -1) + 1) {
                val initialVelocity = x to y

                var position = 0 to 0

                var stepCounter = -1
                while ((position.first <= targetArea.second.first) && (position.second >= targetArea.second.second)) {
                    stepCounter += 1
                    val newVelocity = initialVelocity.calculateTrajectory(stepCounter)

                    position = position.first + newVelocity.first to position.second + newVelocity.second

                    if (targetArea.targetHit(position)) {
                        targetHitCount += 1
                        break
                    }
                }
            }
        }

        return targetHitCount
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day17_test")
    check(part1(testInput).also { println(it) } == 45)
    check(part2(testInput).also { println(it) } == 112)

    val input = readInput("Day17")
    println(part1(input))
    println(part2(input))
}

fun Pair<Int, Int>.calculateTrajectory(steps: Int): Pair<Int, Int> {
    var newX = first
    var newY = second

    for (step in 0 until steps) {
        newX = newX
            .let {
                if (it < 0) {
                    it + 1
                }
                else if (it > 0) {
                    it - 1
                }
                else {
                    0
                }
            }

        newY -= 1
    }

    return newX to newY
}

// ((x1 to y1) to (x2 to y2))
private fun String.getTargetArea(): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    val xStart = indexOf("x=") + 2
    val yStart = indexOf("y=") + 2

    val xCoordinates = substring(xStart, indexOf(",", xStart))
        .split("..")
        .map { it.toInt() }
        .sorted()

    val yCoordinates = substring(yStart)
        .split("..")
        .map { it.toInt() }
        .sortedDescending()

    return (xCoordinates[0] to yCoordinates[0]) to (xCoordinates[1] to yCoordinates[1])
}

private fun Pair<Pair<Int, Int>, Pair<Int, Int>>.targetHit(position: Pair<Int, Int>): Boolean =
    position.first in (first.first..second.first) && position.second in (second.second..first.second)
