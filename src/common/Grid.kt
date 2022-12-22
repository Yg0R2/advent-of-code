package common

class Grid<T> (
    val gridMap: Array<Array<T>>
) {

    fun get(location: Location): T =
        gridMap[location.y][location.x]

    fun get(row: Int, column: Int) =
        gridMap[column][row]

    fun getLocation(square: T): Location {
        gridMap.forEachIndexed { y, line ->
            line.forEachIndexed { x, cell ->
                if (cell == square) {
                    return Location(x, y)
                }
            }
        }

        throw IllegalArgumentException("Grid does not have a start (S) position.")
    }

    fun getXSize(): Int =
        gridMap[0].size

    fun getYSize(): Int =
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
    val x: Int,
    val y: Int
)
