package y2022

import DayX
import y2022.Day10.Instruction.Companion.toInstruction

class Day10 : DayX<Int>(13_140, 0) {

    override fun part1(input: List<String>): Int {
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

    override fun part2(input: List<String>): Int {
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

                    if ((drawingPixelLocation % 40) in (registerX - 1)..(registerX + 1)) {
                        print("#")
                    } else {
                        print(".")
                    }
                }

                registerX += it.updateAmount
            }

        println()

        return 0
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

}
