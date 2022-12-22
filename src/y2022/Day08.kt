package y2022

import DayX
import common.Grid
import common.Grid.Companion.toGrid

class Day08 : DayX<Int>(21, 8) {

    override fun part1(input: List<String>): Int {
        val grid = input.initializeGrid()

        var visibleTreesCount = 0
        for (row: Int in grid.getRowIndices()) {
            for (column: Int in grid.getColumnIndices(row)) {
                if (grid.isVisibleFromEdge(row, column)) {
                    visibleTreesCount++
                }
            }
        }

        return visibleTreesCount
    }

    override fun part2(input: List<String>): Int {
        val grid = input.initializeGrid()

        var maxScenicScore = 0
        for (row: Int in grid.getRowIndices()) {
            for (column: Int in grid.getColumnIndices(row)) {
                val scenicScore = grid.getScenicScore(row, column)

                if (scenicScore > maxScenicScore) {
                    maxScenicScore = scenicScore
                }
            }
        }

        return maxScenicScore
    }

    companion object {
        fun Grid<Int>.getScenicScore(row: Int, column: Int): Int {
            val currentTree = get(row, column)

            return (column - 1 downTo 0).takeUntil { get(row, it) < currentTree }.count() * // LEFT
                    IntRange(column + 1, getRowSize() - 1).takeUntil { get(row, it) < currentTree }.count() * // RIGHT
                    (row - 1 downTo 0).takeUntil { get(it, column) < currentTree }.count() * // UP
                    IntRange(row + 1, getColumnSize(row) - 1).takeUntil { get(it, column) < currentTree }
                        .count() // DOWN
        }

        private fun List<String>.initializeGrid(): Grid<Int> =
            toGrid { it.digitToInt() }

        private fun Grid<Int>.isVisibleFromEdge(row: Int, column: Int): Boolean {
            if ((row == 0) || (row == getRowSize() - 1) || (column == 0) || (column == getColumnSize(row) - 1)) {
                return true
            }

            val currentTree = get(row, column)

            return IntRange(0, column - 1).none { get(row, it) >= currentTree } || // LEFT
                    IntRange(column + 1, getRowSize() - 1).none { get(row, it) >= currentTree } || // RIGHT
                    IntRange(0, row - 1).none { get(it, column) >= currentTree } || // UP
                    IntRange(row + 1, getColumnSize(row) - 1).none { get(it, column) >= currentTree } // DOWN
        }

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
    }

}
