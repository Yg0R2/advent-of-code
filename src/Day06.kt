fun main() {

    fun String.findPattern(sizeOfPattern: Int): Int {
        return sizeOfPattern +
            windowed(sizeOfPattern)
            .withIndex()
            .first { it.value.toCharArray().toSet().size == it.value.length }
            .index
    }

    fun part1(input: String): Int =
        input.findPattern(4)

    fun part2(input: String): Int  =
        input.findPattern(14)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")[0]
    checkResult("test part1", 7) {
        part1(testInput)
    }
    checkResult("test part2", 19) {
        part2(testInput)
    }

    val input = readInput("Day06")[0]
    println(part1(input))
    println(part2(input))

}
