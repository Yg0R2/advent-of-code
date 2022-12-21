package y2022

import DayX

class Day04 : DayX<Int>(2, 4) {

    override fun part1(input: List<String>): Int =
        input.map { it.split(",") }
            .filter { sectionIds ->
                val sectionIds1 = sectionIds[0].getSectionIds()
                val sectionIds2 = sectionIds[1].getSectionIds()

                sectionIds1.containsAll(sectionIds2) ||
                        sectionIds2.containsAll(sectionIds1)
            }
            .size

    override fun part2(input: List<String>): Int =
        input.map { it.split(",") }
            .filter { sectionIds ->
                val sectionIds1 = sectionIds[0].getSectionIds()
                val sectionIds2 = sectionIds[1].getSectionIds()

                sectionIds1.any { sectionIds2.contains(it) }
            }
            .size

    companion object {
        fun String.getSectionIds(): List<Int> =
            split("-")
                .let { IntRange(it[0].toInt(), it[1].toInt()) }
                .toList()
    }

}
