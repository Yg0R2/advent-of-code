import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val sortedInput = input.flatMap { it.split(",") }
            .toIntList()
            .sorted()

        val avgHeight = sortedInput.average().toInt()
        val avgFuelConsumption = sortedInput.sumOf { abs(avgHeight - it) }

        return (sortedInput[0]..sortedInput[sortedInput.size - 1])
            .map { position -> position to sortedInput.sumOf { abs(position - it) } }
            .filter { it.second < avgFuelConsumption }
            .fold(avgFuelConsumption) { currentFuelConsumption: Int, heightToFuelConsumption: Pair<Int, Int> ->
                if (heightToFuelConsumption.second > currentFuelConsumption) {
                    currentFuelConsumption
                }
                else {
                    heightToFuelConsumption.second
                }
            }
    }

    fun part2(input: List<String>): Int {
        val sortedInput = input.flatMap { it.split(",") }
            .toIntList()
            .sorted()

        val avgHeight = sortedInput.average().toInt()
        val avgFuelConsumption = sortedInput.sumOf { (0..abs(avgHeight - it)).sum() }

        return (sortedInput[0]..sortedInput[sortedInput.size - 1])
            .map { position -> position to sortedInput.sumOf { (0..abs(position - it)).sum() } }
            .filter { it.second < avgFuelConsumption }
            .fold(avgFuelConsumption) { currentFuelConsumption: Int, heightToFuelConsumption: Pair<Int, Int> ->
                if (heightToFuelConsumption.second > currentFuelConsumption) {
                    currentFuelConsumption
                }
                else {
                    heightToFuelConsumption.second
                }
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput).also { println(it) } == 37)
    check(part2(testInput).also { println(it) } == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
