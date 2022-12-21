package y2022

import DayX

class Day06 : DayX<Int>(7, 19) {

    override fun part1(input: List<String>): Int =
        input[0].findPattern(4)

    override fun part2(input: List<String>): Int =
        input[0].findPattern(14)

    companion object {
        fun String.findPattern(sizeOfPattern: Int): Int {
            return sizeOfPattern + windowed(sizeOfPattern)
                .withIndex()
                .first { it.value.toCharArray().toSet().size == it.value.length }
                .index
        }
    }

}
