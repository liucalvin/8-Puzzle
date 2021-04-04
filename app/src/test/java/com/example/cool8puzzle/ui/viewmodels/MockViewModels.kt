package com.example.cool8puzzle.ui.viewmodels

//import com.nhaarman.mockito_kotlin.mock
//import com.nhaarman.mockito_kotlin.whenever
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//
//
//fun mockHomeViewModel(
//        mockTiles: StateFlow<List<Int>> = MutableStateFlow(emptyList()),
//        mockStepCount: StateFlow<Int> = MutableStateFlow(0),
//        clickable: StateFlow<Boolean> = MutableStateFlow(true),
//        solved: StateFlow<Boolean> = MutableStateFlow(false)
//) = mock<HomeViewModel>().apply {
//    whenever(tiles).thenReturn(mockTiles)
//    whenever(stepCount).thenReturn(mockStepCount)
//    whenever(boardIsClickable).thenReturn(clickable)
//    whenever(boardIsSolved).thenReturn(solved)
//}