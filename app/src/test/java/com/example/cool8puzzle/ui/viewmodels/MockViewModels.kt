package com.example.cool8puzzle.ui.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.whenever

fun mockHomeViewModel(
        mockTiles: StateFlow<List<Int>> = MutableStateFlow(emptyList()),
        mockStepCount: StateFlow<Int> = MutableStateFlow(0),
        clickable: StateFlow<Boolean> = MutableStateFlow(true),
        solved: StateFlow<Boolean> = MutableStateFlow(false)
) = spy(HomeViewModel()).apply {
    whenever(tiles).thenReturn(mockTiles)
    whenever(stepCount).thenReturn(mockStepCount)
    whenever(boardIsClickable).thenReturn(clickable)
    whenever(boardIsSolved).thenReturn(solved)
}

fun mockSolverStepsViewModel(
    puzzleData: String
) = spy(SolverStepsViewModel(puzzleData, mock()))
