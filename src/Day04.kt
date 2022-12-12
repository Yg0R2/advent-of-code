fun main() {

    fun String.getSectionIds(): List<Int> =
        split("-")
        .let { IntRange(it[0].toInt(), it[1].toInt()) }
            .toList()

    fun part1(input: List<String>): Int {
        return input
            .map { it.split(",") }
            .filter { sectionIds ->
                val sectionIds1 = sectionIds[0].getSectionIds()
                val sectionIds2 = sectionIds[1].getSectionIds()

                sectionIds1.containsAll(sectionIds2) ||
                    sectionIds2.containsAll(sectionIds1)
            }
            .size
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.split(",") }
            .filter { sectionIds ->
                val sectionIds1 = sectionIds[0].getSectionIds()
                val sectionIds2 = sectionIds[1].getSectionIds()

                sectionIds1.any { sectionIds2.contains(it) }
            }
            .size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    checkResult("test part1", 2) {
        part1(testInput)
    }
    checkResult("test part2", 4) {
        part2(testInput)
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))

}
