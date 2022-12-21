package y2021

private val SEGMENT_BITS = arrayOf("1110111", "0010010", "1011101", "1011011", "0111010", "1101011", "1101111", "1010010", "1111111", "1111011")

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { line -> line.split("|").map { it.trim() } }
            .map { it[1] }
            .flatMap { it.split(" ") }
            .count {
                when (it.length) {
                    2, 3, 4, 7 -> true
                    else -> false
                }
            }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.split(" | ") }
            .sumOf { (patterns, outputs) ->
                val digitInputs = patterns
                    .split(" ")
                    .sortedBy { it.length }

                val decodedSegments = decodeSegments(Array(7) { null }, digitInputs, 0)

                outputs
                    .split(" ")
                    .map { it.decode(decodedSegments) }
                    .joinToString("")
                    .toInt()
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput).also { println(it) } == 26)
    check(part2(testInput).also { println(it) } == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

private fun decodeSegments(decodedSegments: Array<Char?>, digitInputs: List<String>, digitLengthCounter: Int): Array<Char?> {
    val reducedSegments = decodedSegments
        .filterNotNull()
        .map { it.toString() }
        .fold(digitInputs[digitLengthCounter]) { acc: String, c: String ->
            acc.replace(c, "")
        }

    return when(digitInputs[digitLengthCounter].length) {
        2 -> { // 1
            val copyOfDecodedSegments = decodedSegments.copyOf()
                .also {
                    it[2] = reducedSegments[0]
                    it[5] = reducedSegments[1]
                }

            val resolved = decodeSegments(copyOfDecodedSegments, digitInputs, digitLengthCounter + 1)

            if (resolved.filterNotNull().size == 7) {
                resolved
            }
            else {
                copyOfDecodedSegments
                    .also {
                        it[2] = reducedSegments[1]
                        it[5] = reducedSegments[0]
                    }

                decodeSegments(copyOfDecodedSegments, digitInputs, digitLengthCounter + 1)
            }
        }
        3 -> { // 7
            val copyOfDecodedSegments = decodedSegments.copyOf()
                .also {
                    it[0] = reducedSegments[0]
                }

            decodeSegments(copyOfDecodedSegments, digitInputs, digitLengthCounter + 1)
        }
        4 -> { // 4
            val copyOfDecodedSegments = decodedSegments.copyOf()
                .also {
                    it[1] = reducedSegments[0]
                    it[3] = reducedSegments[1]
                }

            val resolved = decodeSegments(copyOfDecodedSegments, digitInputs, digitLengthCounter + 1)

            if (resolved.filterNotNull().size == 7) {
                resolved
            }
            else {
                copyOfDecodedSegments
                    .also {
                        it[1] = reducedSegments[1]
                        it[3] = reducedSegments[0]
                    }

                decodeSegments(copyOfDecodedSegments, digitInputs, digitLengthCounter + 1)
            }
        }
        7 -> { // 8
            val copyOfDecodedSegments = decodedSegments.copyOf()
                .also {
                    it[4] = reducedSegments[0]
                    it[6] = reducedSegments[1]
                }

            if (digitInputs.all { it.decode(copyOfDecodedSegments) != -1 }) {
                copyOfDecodedSegments
            }
            else {
                copyOfDecodedSegments
                    .also {
                        it[4] = reducedSegments[1]
                        it[6] = reducedSegments[0]
                    }

                if (digitInputs.all { it.decode(copyOfDecodedSegments) != -1 }) {
                    copyOfDecodedSegments
                }
                else {
                    decodedSegments
                }
            }
        }
        else -> decodeSegments(decodedSegments, digitInputs, digitLengthCounter + 1)
    }
}

private fun String.decode(decodedSegments: Array<Char?>): Int {
    return decodedSegments
        .fold("") { acc: String, c: Char? ->
            if (c?.let { contains(it) } == true) {
                acc + 1
            }
            else {
                acc + 0
            }
        }
        .let { SEGMENT_BITS.indexOf(it) }
}
