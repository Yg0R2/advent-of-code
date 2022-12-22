package common

class Grid<T> (
    private val gridMap: Array<Array<T>>
) {

    fun get(location: Location): T =
        gridMap[location.row][location.column]

    fun get(row: Int, column: Int) =
        gridMap[row][column]

    fun getLocationOf(square: T): Location {
        gridMap.forEachIndexed { rowIndex, line ->
            line.forEachIndexed { columnIndex, cell ->
                if (cell == square) {
                    return Location(columnIndex, rowIndex)
                }
            }
        }

        throw IllegalArgumentException("Grid does not have a start (S) position.")
    }

    fun getColumnIndices(rowIndex: Int = 0): IntRange =
        gridMap[rowIndex].indices

    fun getColumnSize(rowIndex: Int = 0): Int =
        gridMap[rowIndex].size

    fun getRowIndices(): IntRange =
        gridMap.indices

    fun getRowSize(): Int =
        gridMap.size

    companion object {
        inline fun <reified T> List<String>.toGrid(charMapper: (Char) -> T ): Grid<T> =
            map { line ->
                line.toCharArray()
                    .map { charMapper.invoke(it) }
                    .toTypedArray()
            }.toTypedArray()
                .let { Grid(it) }
    }
}

data class Location(
    val column: Int,
    val row: Int
) {

    fun moveDown(step: Int = 1): Location =
        copy(row = row + step)

    fun moveLeft(step: Int = 1): Location =
        copy(column = column - step)

    fun moveRight(step: Int = 1): Location =
        copy(column = column + step)

    fun moveUp(step: Int = 1): Location =
        copy(row = row - step)

}
