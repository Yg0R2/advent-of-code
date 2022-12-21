package y2021

import kotlin.collections.Map.Entry

fun main() {
    fun part1(input: List<String>): Long {
        return input.runSteps(10)
    }

    fun part2(input: List<String>): Long {
        return input.runSteps(40)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput).also { println(it) } == 1588L)
    check(part2(testInput).also { println(it) } == 2188189693529L)

    val input = readInput("Day14")
    println(part1(input).also { check(it == 2587L) })
    println(part2(input))
}

private fun List<String>.runSteps(stepsCount: Int): Long {
    var polymerCounts: Map<String, Long> = this[0]
        .windowed(2)
        .groupingBy { it }
        .eachCount()
        .map { it.key to it.value.toLong() }
        .toMap()

    val pairInsertionRule = this
        .drop(1)
        .filter { it.isNotBlank() }
        .map { it.split("->") }
        .associate { it[0].trim() to it[1].trim() }

    val polymerCounter = this[0]
        .groupingBy { it }
        .eachCount()
        .map { it.key.toString() to it.value.toLong() }
        .toMap()
        .toMutableMap()

    for (i in 1..stepsCount) {
        polymerCounts = polymerCounts.entries
            .fold(mutableMapOf()) { acc: MutableMap<String, Long>, polymerPair: Entry<String, Long> ->
                 val insertPolymer = pairInsertionRule[polymerPair.key]

                polymerCounter.compute(insertPolymer!!) { _, v -> (v ?: 0) + polymerPair.value }

                val key1 = "${polymerPair.key[0]}$insertPolymer"
                val key2 = "$insertPolymer${polymerPair.key[1]}"

                acc.compute(key1) { _, v -> (v ?: 0) + polymerPair.value }
                acc.compute(key2) { _, v -> (v ?: 0) + polymerPair.value }

                acc
            }
            .toMap()
    }

    return polymerCounter.values.maxOf { it } - polymerCounter.values.minOf { it }
}
