import utils.Logger
import utils.readInput
import java.time.LocalDate

abstract class DayX<T>(
    private val testPart1ExpectedResults: List<T>,
    private val testPart2ExpectedResults: List<T>
) {

    constructor(
        testPart1ExpectedResult: T,
        testPart2ExpectedResult: T
    ) : this(listOf(testPart1ExpectedResult), listOf(testPart2ExpectedResult))

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
        println(part1(input))
        println(part2(input))
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
