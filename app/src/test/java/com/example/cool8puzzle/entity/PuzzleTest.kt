package com.example.cool8puzzle.entity

import org.junit.Assert.*
import org.junit.Test

class PuzzleTest {

    @Test
    fun `verify puzzle stores tiles correctly`() {
        val puzzle = Puzzle(arrayOf(
                intArrayOf(1, 2, 3),
                intArrayOf(4, 5, 6),
                intArrayOf(7, 8, 0)
        ))

        assertEquals(
                listOf(1, 2, 3, 4, 5, 6, 7, 8, 0),
                puzzle.tileList()
        )
    }

    @Test
    fun `verify manhattan distance calculated correctly`() {
        val puzzle1 = Puzzle(arrayOf(
                intArrayOf(2, 8, 5),
                intArrayOf(6, 4, 1),
                intArrayOf(7, 0, 3)
        ))
        assertEquals(13, puzzle1.manhattanDistance())

        val puzzle2 = Puzzle(arrayOf(
                intArrayOf(2, 3, 6),
                intArrayOf(5, 4, 1),
                intArrayOf(0, 7, 8)
        ))
        assertEquals(10, puzzle2.manhattanDistance())

        val puzzle3 = Puzzle(arrayOf(
                intArrayOf(1, 2, 3),
                intArrayOf(4, 5, 6),
                intArrayOf(7, 8, 0)
        ))
        assertEquals(0, puzzle3.manhattanDistance())
    }

    @Test
    fun `verify puzzle movement is correct`() {
        val puzzle = Puzzle(arrayOf(
                intArrayOf(2, 8, 5),
                intArrayOf(6, 0, 1),
                intArrayOf(7, 4, 3)
        ))

        val puzzle1 = Puzzle(arrayOf(
                intArrayOf(2, 0, 5),
                intArrayOf(6, 8, 1),
                intArrayOf(7, 4, 3)
        ))
        assertEquals("Move tile 8 down", puzzle.movement(puzzle1))


        val puzzle2 = Puzzle(arrayOf(
                intArrayOf(2, 8, 5),
                intArrayOf(0, 6, 1),
                intArrayOf(7, 4, 3)
        ))
        assertEquals("Move tile 6 right", puzzle.movement(puzzle2))

        val puzzle3 = Puzzle(arrayOf(
                intArrayOf(2, 8, 5),
                intArrayOf(6, 1, 0),
                intArrayOf(7, 4, 3)
        ))
        assertEquals("Move tile 1 left", puzzle.movement(puzzle3))

        val puzzle4 = Puzzle(arrayOf(
                intArrayOf(2, 8, 5),
                intArrayOf(6, 4, 1),
                intArrayOf(7, 0, 3)
        ))
        assertEquals("Move tile 4 up", puzzle.movement(puzzle4))
    }

    @Test
    fun `verify if puzzle is solvable`() {
        val puzzle1 = Puzzle(arrayOf(
                intArrayOf(1, 2, 3),
                intArrayOf(4, 5, 6),
                intArrayOf(7, 8, 0)
        ))
        assertTrue(puzzle1.isSolvable())

        val puzzle2 = Puzzle(arrayOf(
                intArrayOf(7, 1, 8),
                intArrayOf(6, 5, 3),
                intArrayOf(4, 0, 2)
        ))
        assertTrue(puzzle2.isSolvable())

        val puzzle3 = Puzzle(arrayOf(
                intArrayOf(4, 6, 0),
                intArrayOf(5, 7, 2),
                intArrayOf(1, 8, 3)
        ))
        assertFalse(puzzle3.isSolvable())
    }

    @Test
    fun `verify if puzzle is solved`() {
        val puzzle1 = Puzzle(arrayOf(
                intArrayOf(1, 2, 3),
                intArrayOf(4, 5, 6),
                intArrayOf(7, 8, 0)
        ))
        assertTrue(puzzle1.isSolved())

        val puzzle2 = Puzzle(arrayOf(
                intArrayOf(7, 1, 8),
                intArrayOf(6, 5, 3),
                intArrayOf(4, 0, 2)
        ))
        assertFalse(puzzle2.isSolved())

        val puzzle3 = Puzzle(arrayOf(
                intArrayOf(4, 6, 0),
                intArrayOf(5, 7, 2),
                intArrayOf(1, 8, 3)
        ))
        assertFalse(puzzle3.isSolved())
    }

    @Test
    fun `verify neighbours are correct`() {
        val puzzle = Puzzle(arrayOf(
                intArrayOf(2, 8, 5),
                intArrayOf(6, 0, 1),
                intArrayOf(7, 4, 3)
        ))

        val neighhours = mutableSetOf<List<Int>>().apply {
            puzzle.neighbors().forEach {
                this.add(it.tileList())
            }
        }

        assertEquals(4, neighhours.count())

        assertTrue(neighhours.contains(
                listOf(2, 0, 5, 6, 8, 1, 7, 4, 3)
        ))

        assertTrue(neighhours.contains(
                listOf(2, 8, 5, 0, 6, 1, 7, 4, 3)
        ))

        assertTrue(neighhours.contains(
                listOf(2, 8, 5, 6, 1, 0, 7, 4, 3)
        ))

        assertTrue(neighhours.contains(
                listOf(2, 8, 5, 6, 4, 1, 7, 0, 3)
        ))
    }

    @Test
    fun `verify string converted to puzzle correctly`() {
        val puzzleData = "123456780"
        val expectedPuzzle = Puzzle(arrayOf(
            intArrayOf(1, 2, 3),
            intArrayOf(4, 5, 6),
            intArrayOf(7, 8, 0)
        ))
        assertEquals(expectedPuzzle.tileList(), puzzleData.toPuzzle().tileList())
    }

}