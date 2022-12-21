package y2022

import DayX

class Day03 : DayX<Int>(157, 70) {

    /**
     * Lowercase item types a through z have priorities 1 through 26.
     * ascii a-z 97-122
     * Uppercase item types A through Z have priorities 27 through 52.
     * ascii A-Z 65-90
     */
    override fun part1(input: List<String>): Int =
        input.map {
            val middleIndex = it.length / 2

            val leftCompartment = it.subSequence(0, middleIndex)
            val rightCompartment = it.subSequence(middleIndex, it.length)

            leftCompartment.toSet()
                .intersect(rightCompartment.toSet())
                .first()
        }.sumOf { it.getPriority() }

    override fun part2(input: List<String>): Int =
        input.windowed(3, 3)
            .map { group ->
                val rucksack1 = group[0].toSet()
                val rucksack2 = group[1].toSet()
                val rucksack3 = group[2].toSet()

                rucksack1
                    .filter { rucksack2.contains(it) }
                    .first { rucksack3.contains(it) }
            }
            .sumOf { it.getPriority() }

    companion object {
        fun Char.getPriority() =
            if (isLowerCase()) {
                code - 96
            } else {
                code - 38
            }
    }

}
