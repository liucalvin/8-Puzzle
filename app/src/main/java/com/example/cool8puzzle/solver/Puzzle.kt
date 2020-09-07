package com.example.cool8puzzle.solver

import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

class Puzzle(tiles: Array<IntArray>) {
    private val tiles: Array<IntArray>
    private val numList = ArrayList<Int>()
    var movement: String? = null

    companion object {
        private const val LENGTH = 3
        private const val TAG = "Puzzle.java"
        private fun copy(arr: Array<IntArray>): Array<IntArray> {
            val temp = Array(
                LENGTH
            ) { IntArray(LENGTH) }
            for (i in 0 until LENGTH) {
                System.arraycopy(arr[i], 0, temp[i], 0, LENGTH)
            }
            return temp
        }

        // exchanges the elements in places [a1][a2] and [b1][b2]
        private fun exchange(arr: Array<IntArray>, a1: Int, a2: Int, b1: Int, b2: Int) {
            val temp = arr[a1][a2]
            arr[a1][a2] = arr[b1][b2]
            arr[b1][b2] = temp
        }
    }

    init {
        // create defensive copy of the tiles
        this.tiles = copy(tiles)
    }

    fun getTileList(): ArrayList<Int> {

        return toList(this.tiles)
    }

    private fun toList(tiles: Array<IntArray>): ArrayList<Int> {
        var index = 0
        numList.clear()
        for (i in 0 until LENGTH) {
            for (j in 0 until LENGTH) {
                numList.add(tiles[i][j])
                index++
            }
        }
        return numList
    }

    // compares this puzzle to the puzzle of the previous node and returns a description of the movement
    fun movement(moved: Puzzle): String {
        val direction: String
        val movedTileNum: Int
        // find the moved tile
        val initialBlankPosition = this.find(0)
        val finalBlankPosition = moved.find(0)
        movedTileNum = tiles[finalBlankPosition[0]][finalBlankPosition[1]]
        // 4 cases:
        // compare row; if initial > final, then the blank tile has been moved up,
        // so the tile has been moved down.
        direction = when {
            initialBlankPosition[0] > finalBlankPosition[0] -> {
                "down"
            }
            initialBlankPosition[0] < finalBlankPosition[0] -> {
                // blank tile moved down, so tile moved up
                "up"

                // else the movement is horizontal
                // blank tile moved left, so tile is moved right
            }
            initialBlankPosition[1] > finalBlankPosition[1] -> {
                "right"

                // else must be left
            }
            else -> {
                "left"
            }
        }
        return "Move tile $movedTileNum $direction"
    }

    // determines if a puzzle state is solvable by counting inversions
    fun isSolvable(): Boolean {
        // count the inversions: pairs of tiles i, j such that i < j and tiles[i] > tiles[j]
        // for each tile, count how many tiles are greater than it down the list
        // if there are an even number of inversions, the puzzle is valid
        var inversions = 0
        val tempTiles: IntArray = convertTo1DArray(tiles)
        for (i in 0 until LENGTH * LENGTH) {
            val current = tempTiles[i]
            for (j in i + 1 until LENGTH * LENGTH) {
                val tileNum = tempTiles[j]
                if (tileNum != 0) {  // only count the tiles, not the blank tile
                    if (current > tileNum) {
                        inversions++
                    }
                }
            }
        }
        // if there are an even number of inversions, the puzzle is valid
        return inversions % 2 == 0
    }

    // manhattan distance is used as the heuristic in the search algorithm
    // finds the sum of the displacements (sum of vertical and horizonal distances) from a tile's solved spot
    fun manhattanDistance(): Int {
        var count = 0
        for (i in 1 until LENGTH * LENGTH) {   // from 1 to n^2 - 1 inclusive
            val position = mapToArray(i)
            val correct = find(i)
            if (position[0] != correct[0]) {
                count += abs(position[0] - correct[0])
            }
            if (position[1] != correct[1]) {
                count += abs(position[1] - correct[1])
            }
        }
        return count
    }

    // maps a tile's coordinates to its tile number
    // maps [0,0] to [1]
    private fun convertTo1DArray(arr: Array<IntArray>): IntArray {
        val tempTiles = IntArray(LENGTH * LENGTH)
        var count = 0
        for (i in 0 until LENGTH) {
            for (j in 0 until LENGTH) {
                tempTiles[count] = arr[i][j]
                count++
            }
        }
        return tempTiles
    }

    // maps a tile's number to its original spot on the plane
    // maps [1] to [0,0]
    private fun mapToArray(num: Int): IntArray {
        val a = (num - 1) / LENGTH
        val b = (num - 1) % LENGTH
        return intArrayOf(a, b)
    }

    // returns the [row, col] position of the 0 (blank) tile
    private fun find(num: Int): IntArray {
        for (i in 0 until LENGTH) {
            for (j in 0 until LENGTH) {
                if (tiles[i][j] == num) return intArrayOf(i, j)
            }
        }
        return intArrayOf()
    }

    // for the bottom right index = 0
    val isSolved: Boolean
        get() {
            val goal = Array(
                LENGTH
            ) { IntArray(LENGTH) }
            for (i in 0 until LENGTH) {
                for (j in 0 until LENGTH) {
                    goal[j][i] =
                        LENGTH * j + i + 1
                }
            }
            // for the bottom right index = 0
            goal[LENGTH - 1][LENGTH - 1] = 0
            return tiles.contentDeepEquals(goal)
        }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other == null) return false
        if (other.javaClass != this.javaClass) return false
        val temp = other as Puzzle
        return tiles.contentDeepEquals(temp.tiles)
    }

    // all neighboring puzzle instances (all possible switches with the 0 element / blank tile)
    fun neighbors(): Iterable<Puzzle> {
        val puzzles =
            Stack<Puzzle>()
        val zeroLocation = find(0)
        val a1 = zeroLocation[0]
        val a2 = zeroLocation[1]

        // exchange [a1, a2] with its neighbours that exist
        if (zeroLocation[0] != 0) {
            val temp = copy(tiles)
            exchange(temp, a1, a2, a1 - 1, a2)
            puzzles.push(Puzzle(temp))
        }
        if (zeroLocation[0] != LENGTH - 1) {
            val temp = copy(tiles)
            exchange(temp, a1, a2, a1 + 1, a2)
            puzzles.push(Puzzle(temp))
        }
        if (zeroLocation[1] != 0) {
            val temp = copy(tiles)
            exchange(temp, a1, a2, a1, a2 - 1)
            puzzles.push(Puzzle(temp))
        }
        if (zeroLocation[1] != LENGTH - 1) {
            val temp = copy(tiles)
            exchange(temp, a1, a2, a1, a2 + 1)
            puzzles.push(Puzzle(temp))
        }
        return puzzles
    }

    override fun toString(): String {
        return "Puzzle(tiles=${tiles.contentToString()}, tileList=${getTileList()}, movement=$movement\n)"
    }


}