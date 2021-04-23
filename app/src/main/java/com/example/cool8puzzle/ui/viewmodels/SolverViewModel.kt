package com.example.cool8puzzle.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*

class SolverViewModel: ViewModel() {

    private val onPuzzleValidated = MutableStateFlow(ResponseState.IDLE)
    fun onPuzzleValidated(): StateFlow<ResponseState> = onPuzzleValidated

    fun submitPuzzle(solverData: String) {
        onPuzzleValidated.value = isValid(solverData)
    }

    private fun isValid(solverData: String): ResponseState {
        // must be of correct length, with all numbers from 0-8 included, no repeats
        // (0 has been appended for blank tile when getting solverData)
        val regex = "[0-8]{9}".toRegex()
        if (!regex.matches(solverData)) {
            return ResponseState.SYNTAX_ERROR
        }
        val numbers = IntArray(LENGTH * LENGTH)
        // from 0 to 8
        for (i in 0 until LENGTH * LENGTH) {
            // convert each char int the solver data to string to int
            numbers[i] = solverData[i].toString().toInt()
        }
        Arrays.sort(numbers)
        for (i in 0 until LENGTH * LENGTH) {
            if (numbers[i] != i) {
                return ResponseState.FORMAT_ERROR
            }
        }
        return ResponseState.SUCCESS
    }

    enum class ResponseState {
        SUCCESS,
        SYNTAX_ERROR,
        FORMAT_ERROR,
        IDLE
    }

    companion object {
        private const val LENGTH = 3
    }
}
