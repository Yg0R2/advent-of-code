package y2022

import DayX
import common.Grid
import common.Grid.Companion.toGrid
import common.Location

/**
 * a-z  : elevation (0-)
 * S    : current position (a)
 * E    : best signal location (z)
 */
class Day12 : DayX<Int>(31, 0) {

    private lateinit var steps: MutableList<Int>

    override fun part1(input: List<String>): Int {
        val grid: Grid<Int> = input.toGrid {
            when (it) {
                'S' -> S_CODE
                'E' -> E_CODE
                else -> it.code
            }
        }

        val startLocation = grid.getLocationOf(S_CODE)
        val endLocation = grid.getLocationOf(E_CODE)

        steps = mutableListOf()

        move(grid, startLocation, listOf(), endLocation, 0)

        return steps.min() - 1
    }

    override fun part2(input: List<String>): Int {
        return 0
    }

    private fun move(
        grid: Grid<Int>,
        currentLocation: Location,
        previousLocations: List<Location>,
        endLocation: Location,
        stepCount: Int
    ): List<Location> {
        val newPreviousLocations: List<Location> = previousLocations.toMutableList()
            .also { it.add(currentLocation) }

        if (currentLocation == endLocation) {
            steps.add(newPreviousLocations.size)
//            return newPreviousLocations
        } else if (!previousLocations.contains(currentLocation)) {
            val canMoveRange = with(grid.get(currentLocation)) { (this..this + 1) }

            val rightLocation = currentLocation.moveRight()
            if ((rightLocation.column < grid.getColumnSize()) && (grid.get(rightLocation) in canMoveRange)) {
                with(move(grid, rightLocation, newPreviousLocations, endLocation, (stepCount + 1))) {
                    if (size > newPreviousLocations.size) {
                        return this
                    }
                }
            }

            val leftLocation = currentLocation.moveLeft()
            if ((leftLocation.column >= 0) && (grid.get(leftLocation) in canMoveRange)) {
                with(move(grid, leftLocation, newPreviousLocations, endLocation, (stepCount + 1))) {
                    if (size > newPreviousLocations.size) {
                        return this
                    }
                }
            }

            val downLocation = currentLocation.moveDown()
            if ((downLocation.row < grid.getRowSize()) && (grid.get(downLocation) in canMoveRange)) {
                with(move(grid, downLocation, newPreviousLocations, endLocation, (stepCount + 1))) {
                    if (size > newPreviousLocations.size) {
                        return this
                    }
                }
            }

            val upLocation = currentLocation.moveUp()
            if ((upLocation.row >= 0) && (grid.get(upLocation) in canMoveRange)) {
                with(move(grid, upLocation, newPreviousLocations, endLocation, (stepCount + 1))) {
                    if (size > newPreviousLocations.size) {
                        return this
                    }
                }
            }
        }

        return previousLocations
    }

    companion object {
        private const val A_CODE: Int = 'a'.code
        private const val S_CODE: Int = A_CODE - 1
        private const val Z_CODE: Int = 'z'.code
        private const val E_CODE: Int = Z_CODE + 1
    }

}
