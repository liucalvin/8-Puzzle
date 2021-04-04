package com.example.cool8puzzle.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.abs

class HomeViewModel : ViewModel() {

    companion object {
        private const val SIZE = 3
        private const val BLANK_TILE = 9
    }

    private val _boardIsClickable = MutableStateFlow(true)
    val boardIsClickable: StateFlow<Boolean> = _boardIsClickable

    private val _stepCount = MutableStateFlow(0)
    val stepCount: StateFlow<Int> = _stepCount

    private val _tiles = MutableStateFlow<MutableList<Int>>(mutableListOf())
    val tiles: StateFlow<List<Int>> = _tiles

    private val _boardIsSolved = MutableStateFlow(false)
    val boardIsSolved: StateFlow<Boolean> = _boardIsSolved

    init {
        scrambleBoard()
    }

    fun scrambleBoard() {
        resetBoard()
        _tiles.value.shuffle()
        while (!isValidPuzzle) {
            when {
                _tiles.value[0] == 9 -> {
                    switchTiles(1, 2)
                }
                _tiles.value[1] == 9 -> {
                    switchTiles(0, 2)
                }
                else -> {
                    switchTiles(1, 2)
                }
            }
        }
    }

    private fun exchangeTiles(position: Int, blankTileIndex: Int) {
        val temp = _tiles.value.removeAt(position)
        _tiles.value.add(position, BLANK_TILE)
        _tiles.value.removeAt(blankTileIndex)
        _tiles.value.add(blankTileIndex, temp)
//        _tiles.clear()
//        _tiles.addAll(tempTiles)
    }

    private fun resetSteps() {
        _stepCount.value = 0
    }

    fun resetBoard() {
        _tiles.value.clear()
        for (i in 0..8) {
            // adds tiles from 1 to 8 inclusive
            _tiles.value.add(i, i + 1)
        }
        _boardIsClickable.value = true
        _boardIsSolved.value = false
        resetSteps()
    }

    private fun switchTiles(i: Int, j: Int) {
        val temp = _tiles.value[i]
        _tiles.value[i] = _tiles.value[j]
        _tiles.value[j] = temp
    }

    // if even number of inversions, then the puzzle is valid.
    private val isValidPuzzle: Boolean
        get() {
            var inversions = 0
            for (i in 0 until SIZE * SIZE - 1) {
                val frontTile: Int = _tiles.value[i]
                if (frontTile != BLANK_TILE) {
                    for (j in i + 1 until SIZE * SIZE) {
                        val backTile: Int = _tiles.value[j]
                        if (frontTile > backTile && backTile != BLANK_TILE) {
                            inversions++
                        }
                    }
                }
            }
            // if even number of inversions, then the puzzle is valid.
            return inversions % 2 == 0
        }

    private fun moveIsPossible(position: Int): Boolean {
        // check if the move is possible (only manhattan distance of 1 to the empty space)
        var count = 0
        val pos = mapToArray(position)
        val correct = mapToArray(_tiles.value.indexOf(BLANK_TILE))
        if (pos[0] != correct[0]) {
            count += abs(pos[0] - correct[0])
        }
        if (pos[1] != correct[1]) {
            count += abs(pos[1] - correct[1])
        }
        return count == 1
    }

    private fun mapToArray(num: Int): IntArray {
        val a = num / 3
        val b = num % 3
        return intArrayOf(a, b)
    }

    private fun puzzleIsSolved(): Boolean {
        for (i in 0..7) {
            if (_tiles.value[i] != i + 1) {
                return false
            }
        }
        return true
    }

    fun onTileClicked(position: Int) {
        if (moveIsPossible(position) && boardIsClickable.value) {
            val blankTileIndex = _tiles.value.indexOf(BLANK_TILE)
            exchangeTiles(position, blankTileIndex)

            _stepCount.value++

            if (puzzleIsSolved()) {
                _boardIsClickable.value = false
                _boardIsSolved.value = true
            }
        }
    }

}
