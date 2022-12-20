import Instruction.Companion.toInstruction

fun main() {

    fun part1(input: List<String>): Int {
        val signalStrengths: MutableList<Int> = mutableListOf()

        var registerX = 1
        var tic = 0

        input.map { it.toInstruction() }
            .forEach {
                IntRange(1, it.cycles).forEach { _ ->
                    tic++

                    if (((tic - 20) % 40 == 0) || (tic == 20)) {
                        signalStrengths.add(tic * registerX)
                    }
                }

                registerX += it.updateAmount
            }

        return signalStrengths.sum()
    }

    fun part2(input: List<String>) {
        // 40 by 6
        var registerX = 1 // middle of sprite (sprite 3 pixel wide)
        var tic = 0

        input.map { it.toInstruction() }
            .forEach {
                IntRange(1, it.cycles).forEach { _ ->
                    val drawingPixelLocation = tic
                    tic++

                    if (drawingPixelLocation % 40 == 0) {
                        println()
                    }

                    if ((drawingPixelLocation % 40) in (registerX - 1) .. (registerX + 1)) {
                        print("#")
                    } else {
                        print(".")
                    }
                }

                registerX += it.updateAmount
            }

        println()
    }

    // test if implementation meets criteria from the description, like:
    with("Day10") {
        val testInput = readInput("${this}_test")
        checkResult("test part1", 13140) {
            part1(testInput)
        }
        part2(testInput)

        val input = readInput(this)
        println(part1(input))
        println(part2(input))
    }

}

private data class Instruction(
    val cycles: Int,
    val updateAmount: Int
) {

    companion object {
        fun String.toInstruction(): Instruction = when {
            (this == "noop") -> {
                Instruction(1, 0)
            }
            startsWith("addx") -> {
                Instruction(2, substring(5).toInt())
            }
            else -> throw IllegalArgumentException("Unknown instruction: $this")
        }
    }

}
