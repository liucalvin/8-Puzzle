package com.example.cool8puzzle.solver

import com.example.cool8puzzle.entity.Puzzle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class PuzzleSolverTest {

    private lateinit var solver: PuzzleSolver

    @Test
    fun `verify solver moves is correct`() {
        val puzzle = Puzzle(
            arrayOf(
                intArrayOf(1, 2, 3),
                intArrayOf(4, 5, 6),
                intArrayOf(7, 8, 0)
            )
        )
        solver = PuzzleSolver(puzzle)
        assertNotNull(solver.solution())
        assertEquals(solver.moves(), 0)

        val puzzle2 = Puzzle(
            arrayOf(
                intArrayOf(2, 8, 5),
                intArrayOf(6, 4, 1),
                intArrayOf(7, 0, 3)
            )
        )
        solver = PuzzleSolver(puzzle2)
        assertNotNull(solver.solution())
        assertEquals(solver.moves(), 23)

        val puzzle3 = Puzzle(
            arrayOf(
                intArrayOf(2, 3, 6),
                intArrayOf(5, 4, 1),
                intArrayOf(0, 7, 8)
            )
        )
        solver = PuzzleSolver(puzzle3)
        assertNotNull(solver.solution())
        assertEquals(solver.moves(), 18)
    }

}