package com.example.cool8puzzle.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.cool8puzzle.R
import com.example.cool8puzzle.entity.Puzzle
import com.example.cool8puzzle.entity.toPuzzle
import com.example.cool8puzzle.solver.PuzzleSolver

class SolverStepsViewModel(puzzleData: String, puzzleSolver: PuzzleSolver): ViewModel() {

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int> = _error

    val solverSteps: LiveData<List<Puzzle>> = liveData {
        puzzleSolver.solve(puzzleData.toPuzzle())
        val stepsData = puzzleSolver.solution()
        when {
            stepsData == null -> {
                showMessage(R.string.puzzle_not_valid)
                emit(emptyList<Puzzle>())
            }
            stepsData.count() == 1 -> {
                showMessage(R.string.puzzle_already_solved)
                emit(emptyList<Puzzle>())
            }
            else -> emit(stepsData)
        }
    }

    private fun showMessage(message: Int) {
        _error.postValue(message)
    }

}