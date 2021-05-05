package com.example.cool8puzzle.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cool8puzzle.R
import com.example.cool8puzzle.solver.PuzzleSolver
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class SolverStepsViewModelTest {

    private lateinit var subject: SolverStepsViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `verify viewmodel emits puzzle data when valid`() {
        subject = SolverStepsViewModel("123456078", PuzzleSolver())
        assertEquals(3, subject.solverSteps.getOrAwaitValue().size)
    }

    @Test
    fun `verify viewmodel posts error when already solved`() {
        subject = SolverStepsViewModel("123456780", PuzzleSolver())
        assertEquals(0, subject.solverSteps.getOrAwaitValue().size)
        assertEquals(R.string.puzzle_already_solved, subject.error.getOrAwaitValue())
    }

    @Test
    fun `verify viewmodel posts error when already invalid`() {
        subject = SolverStepsViewModel("123456870", PuzzleSolver())
        assertEquals(0, subject.solverSteps.getOrAwaitValue().size)
        assertEquals(R.string.puzzle_not_valid, subject.error.getOrAwaitValue())
    }


}