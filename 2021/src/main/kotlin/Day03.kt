fun main() {
    fun part1(input: List<String>): Int {
        val gammaRate = input[0]
            .mapIndexed { index, _ -> input.getMostCommonBit(index) }
            .joinToString("")
            .toInt(2)

        val epsilonRate = input[0]
            .mapIndexed { index, _ -> input.getLeastCommonBit(index) }
            .joinToString("")
            .toInt(2)

        return gammaRate * epsilonRate
    }

    fun part2(input: List<String>): Int {
        val oxygenGeneratorRating = filterBy(input, 0) { _input, _stringIndex ->
            _input.getMostCommonBit(_stringIndex)
        }
            .first()
            .toInt(2)

        val co2ScrubberRating = filterBy(input, 0) { _input, _stringIndex ->
            _input.getLeastCommonBit(_stringIndex)
        }
            .first()
            .toInt(2)

        return oxygenGeneratorRating * co2ScrubberRating
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

private val commonComparator: (o1: Map.Entry<Char, Int>, o2: Map.Entry<Char, Int>) -> Int = { o1, o2 ->
    if (o1.value == o2.value) -1 else compareValues(o1.value, o2.value)
}

private fun filterBy(input: List<String>, stringIndex: Int, expectedBitBlock: (input: List<String>, stringIndex: Int) -> Char): List<String> {
    val expected = expectedBitBlock.invoke(input, stringIndex)

    return input.filter { it[stringIndex] == expected }
        .let {
            if (it.size != 1) {
                filterBy(it, stringIndex + 1, expectedBitBlock)
            }
            else {
                it
            }
        }
}

private fun List<String>.getCommonBit(stringIndex: Int, filter: (Map<Char, Int>) -> Map.Entry<Char, Int>?): Char {
    return this
        .map { it[stringIndex] }
        .groupingBy { it }
        .eachCount()
        .toSortedMap(compareBy { it.inc() })
        .let { filter(it) }
        ?.key
        ?: throw IllegalArgumentException("Invalid input String.")
}

// filter less common, keep 1 if both has the same amount
private fun List<String>.getLeastCommonBit(stringIndex: Int): Char {
    return getCommonBit(stringIndex) {
        it.minWithOrNull(commonComparator)
    }
}

// filter most common, keep 1 if both has the same amount
private fun List<String>.getMostCommonBit(stringIndex: Int): Char {
    return getCommonBit(stringIndex) {
        it.maxWithOrNull(commonComparator)
    }
}
