package y2021

fun main() {
    fun part1(input: List<String>): Int {
        val grid: Array<Array<Int>> = input.toGrid()

        var flashCounter = 0

        for (step in 0 until 100) {
            grid.increaseEnergyLevel()

            while (grid.hasChargedOctopus()) {
                grid.flash()
            }

            flashCounter += grid.getFlashedCount()
        }

        return flashCounter
    }

    fun part2(input: List<String>): Int {
        val grid: Array<Array<Int>> = input.toGrid()

        val octopusCount = grid.sumOf { it.size }

        var stepCounter = 0

        while (grid.getFlashedCount() != octopusCount) {
            grid.increaseEnergyLevel()

            while (grid.hasChargedOctopus()) {
                grid.flash()
            }

            stepCounter += 1
        }

        return stepCounter
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput).also { println(it) } == 1656)
    check(part2(testInput).also { println(it) } == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}

private fun Array<Array<Int>>.flash() =
    this.forEachIndexed { x, row ->
        row.forEachIndexed { y, _ ->
            if (this[x][y] > 9) {
                flash(this, x, y)

                this[x][y] = 0
            }
        }
    }

private fun flash(grid: Array<Array<Int>>, x: Int, y: Int) {
    for (x1 in ((x - 1)..(x + 1))) {
        for (y1 in ((y - 1)..(y + 1))) {
            if (((x1 == x) && (y1 == y)) || (x1 < 0) || (x1 >= grid.size) || (y1 < 0) || (y1 >= grid[x].size)) {
                continue
            }

            if (grid[x1][y1] != 0) {
                grid[x1][y1] += 1
            }
        }
    }
}

private fun Array<Array<Int>>.getFlashedCount(): Int = this
    .sumOf { row ->
        row.count { it == 0 }
    }


private fun Array<Array<Int>>.hasChargedOctopus(): Boolean =
    this.firstOrNull { row ->
        row.firstOrNull { octopus -> octopus > 9 } != null
    } != null

private fun Array<Array<Int>>.increaseEnergyLevel() =
    this.forEachIndexed { x, row ->
        row.forEachIndexed { y, _ ->
            this[x][y] += 1
        }
    }

private fun List<String>.toGrid() = this
    .map {
        it.split("")
            .toIntList()
            .toTypedArray()
    }
    .toTypedArray()

private fun <T> Array<Array<T>>.runOnEach(block: (Int, Int, T) -> Unit) {
    this.forEachIndexed { x, row ->
        row.forEachIndexed { y, element ->
            block(x, y, element)
        }
    }
}
