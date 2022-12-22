package y2022

import DayX
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlin.math.min

/**
 * Comparing 2 values:
 *  - both values are integers:
 *      - left is lower => order OK
 *      - right is lower => order WRONG
 *      - equals => continue checking
 *  - both values are lists: (compare the 2 list items)
 *      - left runs out elements => order OK
 *      - right runs out elements => order WRONG
 *      - same length => continue checking
 *  - list to integer:
 *      - convert integer to list and continue as 2 lists
 */
class Day13 : DayX<Int>(listOf(13), listOf(0), listOf(4513, 697)) {

    /**
     * 0 = true
     * 1 = true <-
     * 2 = false
     * 3 = true
     * 4 = false
     * 5 = true
     * 6 = false
     * 7 = false
     */
    override fun part1(input: List<String>): Int =
        input.asSequence()
            .windowed(2, 3)
            .map { it[0].readPacket() to it[1].readPacket() }
            .map { isOrdered(it.first, it.second) }
            .withIndex()
            .sumOf {
                if (it.value) {
                    it.index + 1
                } else {
                    0
                }
            }

    override fun part2(input: List<String>): Int {
        return 0
    }

    companion object {
        private val OBJECT_MAPPER = jacksonObjectMapper()

        private fun String.readPacket(): List<Any> =
            OBJECT_MAPPER.readValue(this, object : TypeReference<List<Any>>() {})

        @Suppress("UNCHECKED_CAST")
        private fun isOrdered(left: Any, right: Any): Boolean {
            if ((left is Int) && (right is Int)) {
                if (left > right) {
                    return false
                }
            } else if ((left is List<*>) && (right is List<*>)) {
                val minSize = min(left.size, right.size)

                var orderIsValid = true
                if (minSize != 0) {
                    orderIsValid = isOrdered(left as List<Int>, right as List<Int>)
                }
                return orderIsValid && left.size <= right.size
            } else if ((left is List<*>) && (right is Int)) {
                return isOrdered(left as List<Int>, listOf(right))
            } else if ((left is Int) && (right is List<*>)) {
                return isOrdered(listOf(left), right as List<Int>)
            }

            return true
        }

        private fun isOrdered(left: List<Int>, right: List<Int>): Boolean =
            IntRange(0, min(left.size, right.size) - 1).all {
                isOrdered(left[it], right[it])
            }

    }

}
