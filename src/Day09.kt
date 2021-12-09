fun main() {
    fun part1(input: List<String>): Int {
        val heightmap: Array<Array<Int>> = input.toHeightMap()

        return heightmap
            .getLowestPointCoordinates()
            .sumOf { (x, y) ->
                heightmap[x][y] + 1
            }
    }

    fun part2(input: List<String>): Int {
        val heightmap: Array<Array<Int>> = input.toHeightMap()

        return heightmap
            .getLowestPointCoordinates()
            .map { (x, y) ->
                getBasin(x, y, heightmap, setOf(x to y))
            }
            .sortedByDescending { it.size }
            .take(3)
            .fold(1) { acc: Int, set: Set<Pair<Int, Int>> ->
                acc * set.size
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.toHeightMap(): Array<Array<Int>> = this
    .map { it.split("") }
    .map { it.toIntList() }
    .map { it.toTypedArray() }
    .toTypedArray()

private fun Array<Array<Int>>.getLowestPointCoordinates(): List<Pair<Int, Int>> = this
    .flatMapIndexed { x, heightPoints ->
        heightPoints
            .mapIndexedNotNull { y, currentHeight ->
                if ((x == 0 || this[x - 1][y] > currentHeight) && (x == this.size - 1 || this[x + 1][y] > currentHeight) &&
                    (y == 0 || this[x][y - 1] > currentHeight) && (y == this[x].size - 1 || this[x][y + 1] > currentHeight)) {

                    x to y
                }
                else {
                    null
                }
            }
    }

private fun getBasin(x: Int, y: Int, heightmap: Array<Array<Int>>, basin: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
    val currentHeight = heightmap[x][y]

    if (currentHeight == 9) {
        return basin
    }

    var newBasin = basin.toMutableSet()
        .also { it.add(x to y) }
        .toSet()

    if ((x> 0) && (heightmap[x - 1][y] >= currentHeight + 1)) {
        newBasin = getBasin(x - 1, y, heightmap, newBasin)
    }

    if ((x < heightmap.size - 1) && (heightmap[x + 1][y] >= currentHeight + 1)) {
        newBasin = getBasin(x + 1, y, heightmap, newBasin)
    }

    if ((y > 0) && (heightmap[x][y - 1] >= currentHeight + 1)) {
        newBasin = getBasin(x, y - 1, heightmap, newBasin)
    }

    if ((y < heightmap[x].size - 1) && (heightmap[x][y + 1] >= currentHeight + 1)) {
        newBasin = getBasin(x, y + 1, heightmap, newBasin)
    }

    return newBasin
}
