package y2022

import DayX
import y2022.Day05.Procedure.Companion.getRearrangementProcedure
import y2022.Day05.Stack.Companion.getInitialStack

class Day05 : DayX<String>("CMZ", "MCD") {

    override fun part1(input: List<String>): String {
        val stack = input.getInitialStack()
        val rearrangementProcedure = input.getRearrangementProcedure()

        rearrangementProcedure.forEach {
            stack.rearrangeOneCraterAtOnce(it)
        }

        return stack.getLastCrates()
            .joinToString("")
    }

    override fun part2(input: List<String>): String {
        val stack = input.getInitialStack()
        val rearrangementProcedure = input.getRearrangementProcedure()

        rearrangementProcedure.forEach {
            stack.rearrangeMultipleCraterAtOnce(it)
        }

        return stack.getLastCrates()
            .joinToString("")
    }

    private class Procedure(
        line: String
    ) {

        val amount: Int
        val from: Int
        val to: Int

        init {
            line.replace(PROCEDURE_REGEX, "\$1$DELIMITER\$2$DELIMITER\$3")
                .split(DELIMITER)
                .let {
                    amount = it[0].toInt()
                    from = it[1].toInt()
                    to = it[2].toInt()
                }
        }

        override fun toString(): String =
            "move $amount from $from to $to"

        companion object {
            private const val DELIMITER = " "
            private val PROCEDURE_REGEX = Regex("move ([0-9]+) from ([0-9]+) to ([0-9]+)")

            fun List<String>.getRearrangementProcedure(): List<Procedure> =
                subList(indexOfFirst { it.isEmpty() } + 1, size)
                    .map { Procedure(it) }
        }

    }

    private class Stack(
        stackInput: List<List<String>>,
    ) {

        private val stackMap: MutableMap<Int, MutableList<Char>> = mutableMapOf()

        init {
            for (row: Int in stackInput.size - 1 downTo 0) {
                for (column: Int in 0 until stackInput[row].size) {
                    if (stackMap[column] == null) {
                        stackMap[column] = mutableListOf()
                    }

                    with(stackInput[row][column].elementAt(1)) {
                        if (this != ' ') {
                            stackMap[column]!!.add(this)
                        }
                    }
                }

            }
        }

        fun getLastCrates(): List<Char> =
            stackMap.values
                .map { it.last() }

        fun rearrangeMultipleCraterAtOnce(procedure: Procedure) {
            stackMap[procedure.from - 1]!!
                .removeLast(procedure.amount)
                .let { stackMap[procedure.to - 1]!!.addAll(it) }
        }

        fun rearrangeOneCraterAtOnce(procedure: Procedure) {
            stackMap[procedure.from - 1]!!
                .removeLast(procedure.amount)
                .asReversed()
                .let { stackMap[procedure.to - 1]!!.addAll(it) }
        }

        override fun toString(): String = stackMap.toString()

        companion object {
            fun List<String>.getInitialStack(): Stack =
                take(indexOfFirst { it.isEmpty() } - 1)
                    .map { it.windowed(3, 4) }
                    .let { Stack(it) }

            private fun <T> MutableList<T>.removeLast(n: Int): List<T> {
                val elements = takeLast(n)

                repeat(n) {
                    this.removeLastOrNull()
                }

                return elements
                    .toMutableList()
            }
        }

    }

}
