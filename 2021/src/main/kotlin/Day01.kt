fun main() {
    fun part1(input: List<String>): Int {
        return input.toIntList()
            .windowed(2, 1)
            .filter { it[0] < it[1] }
            .size
    }

    fun part2(input: List<String>): Int {
        return input.toIntList()
            .windowed(3, 1)
            .map { it.sum() }
            .windowed(2, 1)
            .filter { it[0] < it[1] }
            .size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
