fun main() {

    fun Array<Array<Int>>.isVisibleFromEdge(row: Int, column: Int): Boolean {
        if ((row == 0) || (row == size - 1) || (column == 0) || (column == this[row].size - 1)) {
            return true
        }

        val currentTree = this[row][column]

        return IntRange(0, column - 1).none { this[row][it] >= currentTree } || // LEFT
                IntRange(column + 1, size -1).none { this[row][it] >= currentTree} || // RIGHT
                IntRange(0, row - 1).none { this[it][column] >= currentTree } || // UP
                IntRange(row + 1, this[row].size - 1).none { this[it][column] >= currentTree } // DOWN
    }

    fun part1(input: List<String>): Int {
        val grid: Array<Array<Int>> = input.initializeGrid()

        var visibleTreesCount = 0
        for(row: Int in grid.indices) {
            for (column: Int in grid[row].indices) {
                if (grid.isVisibleFromEdge(row, column)) {
                    visibleTreesCount++
                }
            }
        }

        return visibleTreesCount
    }

    fun Array<Array<Int>>.getScenicScore(row: Int, column: Int): Int {
        val currentTree = this[row][column]

        return (column - 1 downTo 0).takeUntil { this[row][it] < currentTree }.count() * // LEFT
                IntRange(column + 1, size -1).takeUntil { this[row][it] < currentTree}.count() * // RIGHT
                (row - 1 downTo 0).takeUntil { this[it][column] < currentTree }.count() * // UP
                IntRange(row + 1, this[row].size - 1).takeUntil { this[it][column] < currentTree }.count() // DOWN
    }

    fun part2(input: List<String>): Int {
        val grid: Array<Array<Int>> = input.initializeGrid()

        var maxScenicScore = 0
        for(row: Int in grid.indices) {
            for (column: Int in grid[row].indices) {
                val scenicScore = grid.getScenicScore(row, column)

                if (scenicScore > maxScenicScore) {
                    maxScenicScore = scenicScore
                }
            }
        }

        return maxScenicScore
    }

    // test if implementation meets criteria from the description, like:
    with("Day08") {
        val testInput = readInput("${this}_test")
        checkResult("test part1", 21) {
            part1(testInput)
        }
        checkResult("test part2", 8) {
            part2(testInput)
        }

        val input = readInput(this)
        println(part1(input))
        println(part2(input))
    }

}

private fun List<String>.initializeGrid(): Array<Array<Int>> =
    map { line ->
        line.toCharArray()
            .map { it.digitToInt() }
            .toTypedArray()
    }.toTypedArray()

private inline fun <T> Iterable<T>.takeUntil(predicate: (T) -> Boolean): List<T> {
    val list = mutableListOf<T>()
    for (item in this) {
        list.add(item)

        if (!predicate(item)) {
            break
        }
    }
    return list
}
