package y2021

fun main() {
    fun part1(input: List<String>): Int {
        // y (column) to x (row)
        val coordinates: List<Pair<Int, Int>> = input.getCoordinates()

        // y => horizontal (row)
        // x => vertical (column)
        val folds: List<Pair<String, Int>> = input.getFoldAlongs()

        return coordinates
            .fold(mutableListOf()) { acc: MutableList<Pair<Int, Int>>, coordinate: Pair<Int, Int> ->
                acc.add(foldCoordinate(coordinate, folds[0]))

                acc
            }
            .filter { it.first >= 0 && it.second >= 0 }
            .toSet()
            .count()
    }

    fun part2(input: List<String>): String {
        // y (column) to x (row)
        val coordinates: List<Pair<Int, Int>> = input.getCoordinates()

        // y => horizontal (row)
        // x => vertical (column)
        val folds: List<Pair<String, Int>> = input.getFoldAlongs()

        val foldedResult: List<Pair<Int, Int>> = folds
            .fold(coordinates.toMutableList()) { newCoordinates: MutableList<Pair<Int, Int>>, foldCoordinate: Pair<String, Int> ->
                newCoordinates.fold(mutableListOf()) { acc: MutableList<Pair<Int, Int>>, coordinate: Pair<Int, Int> ->
                    acc.add(foldCoordinate(coordinate, foldCoordinate))

                    acc
                }
            }

        val maxColumn = foldedResult.maxOf { it.first }
        val maxRow = foldedResult.maxOf { it.second }

        return foldedResult
            .fold(MutableList(maxRow + 1) { MutableList(maxColumn + 1) { " " } }) { acc: MutableList<MutableList<String?>>, pair: Pair<Int, Int> ->
                acc[pair.second][pair.first] = "#"

                acc
            }
            .joinToString("\n") { it.joinToString("") }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput).also { println(it) } == 17)
    check(part2(testInput).also { println(it) } == "#####\n#   #\n#   #\n#   #\n#####")

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.getCoordinates(): List<Pair<Int, Int>> =
    this.filter { !it.startsWith("fold along") }
        .filter { it.isNotBlank() }
        .map { it.split(",") }
        .map { it[0].toInt() to it[1] .toInt()}

private fun List<String>.getFoldAlongs(): List<Pair<String, Int>> =
    this.filter { it.startsWith("fold along") }
        .map { it.replace("fold along", "").trim() }
        .map { it.split("=") }
        .map { it[0] to it[1].toInt() }

private fun foldCoordinate(coordinate: Pair<Int, Int>, foldCoordinate: Pair<String, Int>): Pair<Int, Int> =
    if (foldCoordinate.first == "x") {
        val foldX = foldCoordinate.second

        if (coordinate.first < (foldX)) {
            coordinate
        }
        else {
            ((2 * foldX) - coordinate.first) to coordinate.second
        }
    }
    else {
        val foldY = foldCoordinate.second

        if (coordinate.second < foldY) {
            coordinate
        }
        else {
            coordinate.first to ((2 * foldY) - coordinate.second)
        }
    }
