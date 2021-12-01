fun main() {
    fun part1(input: List<String>): Int {
        val parsedInput = input.toIntList()

        return parsedInput
            .dropLast(1)
            .filterIndexed { index, element -> element < parsedInput[index + 1] }
            .size
    }

    fun part2(input: List<String>): Int {
        val parsedInput = input.toIntList()

        return parsedInput
            .dropLast(3)
            .filterIndexed { index, _ -> parsedInput.subList(index, index + 3).sum() < parsedInput.subList(index + 1, index + 1 + 3).sum() }
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
