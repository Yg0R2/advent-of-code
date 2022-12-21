package y2021

import java.util.ArrayDeque
import java.util.Deque

private val AUTO_COMPLETE_CHARACTER_SCORE = mapOf(
    ')' to 1L,
    ']' to 2L,
    '}' to 3L,
    '>' to 4L
)

private val ILLEGAL_CHARACTER_SCORE = mapOf(
    ')' to 3,
    ']' to 57,
    '}' to 1197,
    '>' to 25137
)

private val TAGS = mapOf(
    '(' to ')',
    '[' to ']',
    '{' to '}',
    '<' to '>'
)

fun main() {
    fun getCorruptedTag(stack: Deque<Char>) = { corruptedTag: String, currentTag: Char ->
        if (TAGS.containsKey(currentTag)) {
            stack.push(currentTag)

            corruptedTag
        }
        else if (TAGS[stack.peek()] == currentTag) {
            stack.pop()

            corruptedTag
        }
        else if (corruptedTag.isNotBlank()) {
            corruptedTag
        }
        else {
            currentTag.toString()
        }
    }

    fun part1(input: List<String>): Int {
        return input
            .map { it.fold("", getCorruptedTag(ArrayDeque())) }
            .flatMap { it.toList() }
            .sumOf { ILLEGAL_CHARACTER_SCORE[it] ?: 0 }
    }

    fun part2(input: List<String>): Int {
        return input
            .asSequence()
            .filter {
                it.fold("", getCorruptedTag(ArrayDeque())).isBlank()
            }
            .map {
                it.fold(ArrayDeque()) { stack: Deque<Char>, currentTag: Char ->
                    if (TAGS.containsKey(currentTag)) {
                        stack.push(currentTag)
                    }
                    else if (TAGS[stack.peek()] == currentTag) {
                        stack.pop()
                    }

                    stack
                }
            }
            .map { it.toList() }
            .map {
                it.fold(0L) { acc: Long, tag: Char ->
                    acc * 5 + (AUTO_COMPLETE_CHARACTER_SCORE[TAGS[tag]] ?: 0L)
                }
            }
            .sorted()
            .toList()
            .let { it[it.size / 2] }
            .toInt()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput).also { println(it) } == 26397)
    check(part2(testInput).also { println(it) } == 288957)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
