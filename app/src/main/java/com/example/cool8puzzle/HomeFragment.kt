package com.example.cool8puzzle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.math.abs

class HomeFragment : Fragment(), OnItemClickListener,
    View.OnClickListener {
    private lateinit var tilesAdapter: TilesAdapter
    private lateinit var gridView: GridView
    private lateinit var tiles: MutableList<Int>
    private lateinit var stepCounter: TextView
    private lateinit var solvedText: TextView
    private var blankTile = 9
    private var stepCount = 0
    private var boardIsClickable = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        stepCounter = view.findViewById(R.id.home_step_counter)
        solvedText = view.findViewById(R.id.home_solved_text)

        val resetButton = view.findViewById<Button>(R.id.home_reset_button)
        val scrambleButton = view.findViewById<Button>(R.id.home_scramble_button)
        tiles = mutableListOf()
        scrambleBoard()
        gridView = view.findViewById(R.id.home_gridview)
        tilesAdapter = TilesAdapter(tiles, view.context)
        gridView.adapter = tilesAdapter
        gridView.onItemClickListener = this
        resetButton.setOnClickListener(this)
        scrambleButton.setOnClickListener(this)
        return view
    }

    private fun resetBoard() {
        tiles.clear()
        for (i in 0..7) {
            // adds tiles from 1 to 8 inclusive
            tiles.add(i, i + 1)
        }
        // the blank tile:
//        blankTile = Tile(8 + 1)
        tiles.add(8, 9)
        boardIsClickable = true
        resetSteps()
    }

    private fun resetSteps() {
        solvedText.visibility = View.INVISIBLE
        stepCount = 0
        stepCounter.text = getString(R.string.steps, stepCount)
    }

    private fun scrambleBoard() {
        tiles.clear()
        resetBoard()
        tiles.shuffle()
        while (!isValidPuzzle) {
//            Log.d(TAG, "tiles[0]: ${tiles[0]}, tiles[1]: ${tiles[1]}, tiles[2]: ${tiles[2]} ")
            when {
                tiles[0] == 9 -> {
                    switchTiles(1, 2)
                }
                tiles[1] == 9 -> {
                    switchTiles(0, 2)
                }
                else -> {
                    switchTiles(1, 2)
                }
            }
        }
    }

    // if even number of inversions, then the puzzle is valid.
    private val isValidPuzzle: Boolean
        get() {
            var inversions = 0
            for (i in 0 until LENGTH * LENGTH - 1) {
                val frontTile: Int = tiles[i]
                if (frontTile != blankTile) {
                    for (j in i + 1 until LENGTH * LENGTH) {
                        val backTile: Int = tiles[j]
                        if (frontTile > backTile && backTile != blankTile) {
                            inversions++
                        }
                    }
                }
            }
//                    Log.e(TAG, "************** Number of Inversions: $inversions");
            // if even number of inversions, then the puzzle is valid.
            return inversions % 2 == 0
        }

    private fun switchTiles(i: Int, j: Int) {
        val temp = tiles[i]
        tiles[i] = tiles[j]
        tiles[j] = temp
        /*   tiles.removeAt(i)
           tiles.add(i, b)
           tiles.removeAt(j)
           tiles.add(j, a)*/
//        Log.d(TAG, "Switching $a at position $i and $b and position $j")
//        Log.d(TAG, "Switched: tiles[0]: ${tiles[0]}, tiles[1]: ${tiles[1]}, tiles[2]: ${tiles[2]} ")
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        // check if the move is possible (only distance of 1 to the empty space)
        if (moveIsPossible(position) && boardIsClickable) {
            // exchange the tile with the blank tile
            val blankTileIndex = tiles.indexOf(blankTile)
            exchangeTiles(position, blankTileIndex)
            stepCount++
            stepCounter.text = getString(R.string.steps, stepCount)
//            Log.d(TAG, isValidPuzzle.toString())
            tilesAdapter.notifyDataSetChanged()
            gridView.adapter = tilesAdapter
            gridView.invalidateViews()
            if (puzzleIsSolved()) {
                boardIsClickable = false
                solvedText.visibility = View.VISIBLE
            }
        }
    }

    private fun puzzleIsSolved(): Boolean {
        for (i in 0..7) {
            if (tiles[i] != i + 1) {
                return false
            }
        }
        return true
    }

    private fun moveIsPossible(position: Int): Boolean {
        // Reminder: position is 0-indexed
        var count = 0
        val pos = mapToArray(position)
        val correct = mapToArray(tiles.indexOf(blankTile))
        if (pos[0] != correct[0]) {
            count += abs(pos[0] - correct[0])
        }
        if (pos[1] != correct[1]) {
            count += abs(pos[1] - correct[1])
        }
        return count == 1
    }

    private fun exchangeTiles(position1: Int, position2: Int) {
        // make a copy of the tiles
        val tempTiles = mutableListOf<Int>()
        tempTiles.addAll(tiles)

        val temp: Int = tempTiles.removeAt(position1)
        tempTiles.add(position1, blankTile)

        tempTiles.removeAt(position2)
        tempTiles.add(position2, temp)
        tiles.clear()
        tiles.addAll(tempTiles)
    }

    private fun mapToArray(num: Int): IntArray {
        val a = num / 3
        val b = num % 3
        return intArrayOf(a, b)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.home_reset_button -> {
                resetBoard()
                tilesAdapter.notifyDataSetChanged()
                gridView.adapter = tilesAdapter
                gridView.invalidateViews()
            }
            R.id.home_scramble_button -> {
                scrambleBoard()
                tilesAdapter.notifyDataSetChanged()
                gridView.adapter = tilesAdapter
                gridView.invalidateViews()
            }
        }
    }

    companion object {
        //        private const val TAG = "Home Fragment"
        private const val LENGTH = 3
    }
}