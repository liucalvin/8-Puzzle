package com.example.cool8puzzle.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cool8puzzle.ui.viewmodels.SolverViewModel.ResponseState
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SolverViewModelTest {

    private lateinit var subject: SolverViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        subject = SolverViewModel()
    }

    @Test
    fun `assert initial state`() {
        assertEquals(ResponseState.IDLE, subject.onPuzzleValidated().value)
    }

    @Test
    fun `verify puzzle validation is correct`() {
        val puzzleData1 = "123456780"
        subject.submitPuzzle(puzzleData1)
        assertEquals(ResponseState.SUCCESS, subject.onPuzzleValidated().value)

        val puzzleData2 = "123456"
        subject.submitPuzzle(puzzleData2)
        assertEquals(ResponseState.SYNTAX_ERROR, subject.onPuzzleValidated().value)

        val puzzleData3 = "123456abc"
        subject.submitPuzzle(puzzleData3)
        assertEquals(ResponseState.SYNTAX_ERROR, subject.onPuzzleValidated().value)

        val puzzleData4 = "123123123"
        subject.submitPuzzle(puzzleData4)
        assertEquals(ResponseState.FORMAT_ERROR, subject.onPuzzleValidated().value)
    }


}