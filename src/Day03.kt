fun main() {

    fun Char.getPriority() =
        if (isLowerCase()) {
            code - 96
        } else {
            code - 38
        }

    /**
     * Lowercase item types a through z have priorities 1 through 26.
     * ascii a-z 97-122
     * Uppercase item types A through Z have priorities 27 through 52.
     * ascii A-Z 65-90
     */
    fun part1(input: List<String>): Int {
        return input
            .map {
                val middleIndex = it.length / 2

                val leftCompartment = it.subSequence(0, middleIndex)
                val rightCompartment = it.subSequence(middleIndex, it.length)

                leftCompartment.toSet()
                    .intersect(rightCompartment.toSet())
                    .first()
            }
            .sumOf { it.getPriority() }
    }

    fun part2(input: List<String>): Int {
        return input
            .windowed(3, 3)
            .map { group ->
                val rucksack1 = group[0].toSet()
                val rucksack2 = group[1].toSet()
                val rucksack3 = group[2].toSet()

                rucksack1
                    .filter { rucksack2.contains(it) }
                    .first { rucksack3.contains(it) }
            }
            .sumOf { it.getPriority() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    checkResult("test part1", 157) {
        part1(testInput)
    }
    checkResult("test part2", 70) {
        part2(testInput)
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))

}
