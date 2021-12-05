import java.util.concurrent.atomic.AtomicBoolean

fun main() {
    fun initializeBoards(input: List<String>) = input
        .drop(2)
        .windowed(5, 6)
        .map { BingoBoard(it) }

    fun part1(input: List<String>): Int {
        val boards: List<BingoBoard> = initializeBoards(input)

        var result = 0

        input[0].split(",")
            .map { it.toInt() }
            .any { currentDraw ->
                boards.any {
                    it.drawNumber(currentDraw)

                    if (it.hasBingo.get()) {
                        result = it.sumOfNotDrawn() * currentDraw
                    }

                    result != 0
                }
            }

        return result
    }

    fun part2(input: List<String>): Int {
        val boards: List<BingoBoard> = initializeBoards(input)

        var result = 0

        input[0].split(",")
            .map { it.toInt() }
            .any { currentDraw ->
                boards.filter { !it.hasBingo.get() }
                    .forEach {
                        it.drawNumber(currentDraw)

                        if (it.hasBingo.get()) {
                            result = it.sumOfNotDrawn() * currentDraw
                        }
                    }

                result != 0 && boards.all { it.hasBingo.get() }
            }

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

private class BingoBoard(input: List<String>) {

    val hasBingo = AtomicBoolean(false)

    private val board: Array<Array<BingoBoardNumber>>
    private val boardByColumn:Array<Array<BingoBoardNumber>>

    init {
        board = input
            .map { row ->
                row.split(" ")
                    .filter { it.isNotBlank() }
                    .map { BingoBoardNumber(it.toInt()) }
                    .toTypedArray()
            }
            .toTypedArray()

        boardByColumn = board.mapIndexed { rowIndex, _ ->
                board.map { it[rowIndex] }
                    .toTypedArray()
            }
            .toTypedArray()
    }

    fun drawNumber(drawn: Int) {
        board.forEach { row ->
                row.filter { it.number == drawn }
                    .forEach { it.drawn = true }
            }

        hasBingo.set(isColumnBing() || isRowBing())
    }

    fun sumOfNotDrawn(): Int = board
        .flatten()
        .filter { !it.drawn }
        .sumOf { it.number }

    private fun isColumnBing(): Boolean {
        return boardByColumn.any { column -> column.all { it.drawn } }
    }

    private fun isRowBing(): Boolean {
        return board.any { row -> row.all { it.drawn } }
    }

}

private data class BingoBoardNumber(
    var number: Int,
    var drawn: Boolean = false
)
