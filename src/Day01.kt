private fun getElfs(input: List<String>): List<Elf> {
    val elfs: MutableList<Elf> = mutableListOf()

    var elfCounter = 0
    input.forEach {
        if (it.isBlank()) {
            elfCounter++
        } else {
            if (elfs.size <= elfCounter) {
                elfs.add(Elf())
            }

            elfs[elfCounter]
                .candies
                .add(it.toInt())
        }
    }

    return elfs
}

fun main() {

    fun part1(input: List<String>): Int {
        return getElfs(input)
            .maxOf { it.candies.sum() }
    }

    fun part2(input: List<String>): Int {
        return getElfs(input)
            .map { it.candies.sum() }
            .sortedDescending()
            .take(3)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    checkResult("test part1", 24000) {
        part1(testInput)
    }
    checkResult("test part2", 45000) {
        part2(testInput)
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

private class Elf {

    val candies: MutableList<Int> = mutableListOf()

}
