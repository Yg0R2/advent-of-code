import utils.Logger
import utils.readInput
import java.time.LocalDate

abstract class DayX<T>(
    private val testPart1ExpectedResults: List<T>,
    private val testPart2ExpectedResults: List<T>,
    private val part1InvalidResults: List<T> = emptyList(),
    private val part2InvalidResults: List<T> = emptyList()
) {

    constructor(
        testPart1ExpectedResult: T,
        testPart2ExpectedResult: T,
        part1InvalidResult: T? = null,
        part2InvalidResult: T? = null
    ) : this(
        listOf(testPart1ExpectedResult),
        listOf(testPart2ExpectedResult),
        listOfNotNull(part1InvalidResult),
        listOfNotNull(part2InvalidResult)
    )

    private val inputName = javaClass.name.replace(".", "/")


    fun run() {
        testPart1ExpectedResults.forEachIndexed { index, expectedResult ->
            val testName = "test${index.toTestIndexSuffix()}"

            val testInput = readInput("${inputName}_$testName")
            with(part1(testInput)) {
                println("$testName part1: $this")
                check(this == expectedResult)
            }
        }

        testPart2ExpectedResults.forEachIndexed { index, expectedResult ->
            val testName = "test${index.toTestIndexSuffix()}"

            val testInput = readInput("${inputName}_$testName")
            with(part2(testInput)) {
                println("$testName part2: $this")
                check(this == expectedResult)
            }
        }

        val input = readInput(inputName)
        with(part1(input)) {
            println("part1: $this")

            part1InvalidResults.forEach {
                check(it != this)
            }
        }
        with(part2(input)) {
            println("part2: $this")

            part2InvalidResults.forEach {
                check(it != this)
            }
        }
    }

    protected abstract fun part1(input: List<String>): T

    protected abstract fun part2(input: List<String>): T

    companion object {
        fun runTest(year: Int = LocalDate.now().year, day: Int, vararg args: String) {
            val className = "y$year.Day${day.toString().padStart(2, '0')}"
            val dayX: DayX<*> = Class.forName(className)
                .constructors[0]
                .newInstance()
                .let { it as DayX<*> }

            if (args.isNotEmpty()) {
                Logger.isDebugEnabled = args.contains("--debug")
            }

            dayX.run()
        }

        private fun Int.toTestIndexSuffix(): String =
            if (this == 0) {
                ""
            } else {
                "${this + 1}"
            }

    }

}
