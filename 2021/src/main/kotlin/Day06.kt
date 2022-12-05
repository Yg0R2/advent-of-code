fun main() {
    fun part1(input: List<String>): Long {
        val fishTank = FishTank(input)

        for (i in 0 until 80) {
            fishTank.nextDay()
        }

        return fishTank.getFishCount()
    }

    fun part2(input: List<String>): Long {
        val fishTank = FishTank(input)

        for (i in 0 until 256) {
             fishTank.nextDay()
        }

        return fishTank.getFishCount()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539L)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

private class FishTank(
    input: List<String>
) {

    companion object {
        private const val NEW_BORN_INTERNAL_TIMER = 8
        private const val NEXT_CHILD_INTERNAL_TIMER = 6
    }

    private val tank: Array<Long> = Array(NEW_BORN_INTERNAL_TIMER + 1) { 0L }

    init {
        input.flatMap { it.split(",") }
            .forEach { tank[it.toInt()] += 1L }
    }

    fun getFishCount() = tank.sum()

    fun nextDay() {
        val willBread = tank[0]

        for(index in 1..NEW_BORN_INTERNAL_TIMER) {
            tank[index - 1] = tank[index]
        }

        tank[NEW_BORN_INTERNAL_TIMER] = willBread
        tank[NEXT_CHILD_INTERNAL_TIMER] += willBread
    }

}
