package com.example.cool8puzzle.entity

import com.example.cool8puzzle.ui.fragments.SolverStepsFragment

fun String.toPuzzle(): Puzzle {
    val tiles = Array(
        SolverStepsFragment.LENGTH
    ) { IntArray(SolverStepsFragment.LENGTH) }
    var index = 0
    for (i in 0 until SolverStepsFragment.LENGTH) {
        for (j in 0 until SolverStepsFragment.LENGTH) {
            tiles[i][j] = this[index++].toString().toInt()
        }
    }
    return Puzzle(tiles)
}