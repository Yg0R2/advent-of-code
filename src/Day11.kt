import Monkey.Companion.toMonkey

fun main(vararg args: String) {
    val isDebugEnabled = args.isNotEmpty() && args.contains("--debug")

    fun List<Monkey>.activity(relief: Long? = null) {
        val largestWorryLevelDivider = map { it.worryLevelDivider }
            .reduce { acc, element -> acc * element }

        forEach { monkey ->
            if (isDebugEnabled) {
                println("Monkey ${monkey.id}")
            }

            val items = monkey.items.toList()

            monkey.items.removeAll { true }

            items.forEach { worryLevel ->
                monkey.inspectionCounter++
                if (isDebugEnabled) {
                    println("  Monkey inspects an item with a worry level of $worryLevel.")
                }

                val newWorryLevel = monkey.operation.invoke(worryLevel)
                    .also {
                        if (isDebugEnabled) {
                            println("    Worry level is changed to $it.")
                        }
                    }.let {
                        if (relief != null) {
                            (it / relief)
                                .also {
                                    if (isDebugEnabled) {
                                        println("    Monkey gets bored with item. Worry level is divided by $relief to $it.")
                                    }
                                }
                        } else {
                            it
                        }
                    }.let {
                        // TODO: without this the newWorryLevel would overflow the Long value;
                        //  but with BigDecimal the issue is not present and the result is not the expected
                        with(it % largestWorryLevelDivider) {
                            if (this > 0) {
                                this
                            } else {
                                it
                            }
                        }
                    }

                val throwToMonkey = if (newWorryLevel % monkey.worryLevelDivider == 0L) {
                    if (isDebugEnabled) {
                        println("    Current worry level is divisible by ${monkey.worryLevelDivider}.")
                    }
                    monkey.ifTestTrue
                } else {
                    if (isDebugEnabled) {
                        println("    Current worry level is not divisible by ${monkey.worryLevelDivider}.")
                    }
                    monkey.ifTestFalse
                }

                if (isDebugEnabled) {
                    println("    Item with worry level $newWorryLevel is thrown to monkey $throwToMonkey.")
                }

                this[throwToMonkey].items.add(newWorryLevel)
            }
        }
    }

    fun List<Monkey>.getMonkeyBusiness(): Long {
        if (isDebugEnabled) {
            forEach {
                println("Monkey ${it.id} inspected items ${it.inspectionCounter} times.")
            }
        }

        return map { it.inspectionCounter }
            .sortedDescending()
            .take(2)
            .reduce { accumulator, element -> accumulator * element }
    }

    fun part1(input: List<String>): Long {
        val monkeys = input.windowed(6, 7)
            .map { it.toMonkey() }

        IntRange(1, 20).forEach { _ ->
            monkeys.activity(3L)
        }

        return monkeys.getMonkeyBusiness()
    }

    fun part2(input: List<String>): Long {
        val monkeys = input.windowed(6, 7)
            .map { it.toMonkey() }

        IntRange(1, 10_000).forEach { i ->
            monkeys.activity()
        }

        return monkeys.getMonkeyBusiness()
    }

    // test if implementation meets criteria from the description, like:
    with("Day11") {
        val testInput = readInput("${this}_test")
        checkResult("test part1", 10605) {
            part1(testInput)
        }
        checkResult("test part2", 2713310158) {
            part2(testInput)
        }

        val input = readInput(this)
        println(part1(input))
        println(part2(input))
    }

}

private data class Monkey(
    val id: Int,
    val items: MutableList<Long>,
    var inspectionCounter: Long = 0L,
    val operation: (Long) -> Long,
    val worryLevelDivider: Long,
    val ifTestTrue: Int,
    val ifTestFalse: Int
) {

    companion object {
        private val ID_REGEX = Regex("Monkey (.*?):")

        fun List<String>.toMonkey(): Monkey {
            val id = ID_REGEX
                .find(this[0])!!
                .groups[1]!!
                .value
                .toInt()
            val items = this[1]
                .substring(18)
                .split(",")
                .map { it.trim() }
                .map { it.toLong() }
                .toMutableList()
            val operation = with(this[2]) {
                val secondOperand = substring(25).trim()

                when (this[23]) {
                    '+' -> {
                        if (secondOperand == "old") {
                            { old: Long -> old + old }
                        } else {
                            { old: Long -> old + secondOperand.toLong() }
                        }
                    }

                    '*' -> {
                        if (secondOperand == "old") {
                            { old: Long -> old * old }
                        } else {
                            { old: Long -> old * secondOperand.toLong() }
                        }
                    }

                    else -> throw IllegalArgumentException("Unknown operation: $this")
                }
            }
            val worryLevelDivider = this[3]
                .substring(21)
                .trim()
                .toLong()

            return Monkey(
                id = id,
                items = items,
                operation = operation,
                worryLevelDivider = worryLevelDivider,
                ifTestTrue = this[4].substring(29).trim().toInt(),
                ifTestFalse = this[5].substring(30).trim().toInt()
            )
        }
    }

}
