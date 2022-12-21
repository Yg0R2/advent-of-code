package y2022

import DayX

class Day01 : DayX<Int>(24000, 45000) {

    override fun part1(input: List<String>): Int =
        getElfs(input)
            .maxOf { it.candies.sum() }

    override fun part2(input: List<String>): Int =
        getElfs(input)
            .map { it.candies.sum() }
            .sortedDescending()
            .take(3)
            .sum()

    private fun getElfs(input: List<String>): List<Elf> {
        val elfs: MutableList<Elf> = mutableListOf()

        var elfCounter = 0
        input.forEach {
            if (it.isBlank()) {
                elfCounter++
            } else {
                if (elfs.size <= elfCounter) {
                    elfs.add(Elf())
                }

                elfs[elfCounter]
                    .candies
                    .add(it.toInt())
            }
        }

        return elfs
    }

    private class Elf {

        val candies: MutableList<Int> = mutableListOf()

    }

}
