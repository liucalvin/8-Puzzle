package com.example.cool8puzzle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cool8puzzle.solver.Puzzle
import com.example.cool8puzzle.solver.PuzzleSolver

class SolverStepsFragment : Fragment() {
    private lateinit var resetButton: Button
    private var puzzle: Puzzle? = null
    private var solverDataReceived = false

    companion object {
        private const val TAG = "SolverStepsFragment"
        private const val LENGTH = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // retrieve bundle data from solver
        val bundle = this.arguments
        if (bundle != null) {
            solverDataReceived = true
            val puzzleData = bundle.getString("solverKey")
//            Log.d(TAG, "Bundle received: $puzzleData")

            // converts the data to a puzzle; if invalid, displays toast (below)
            puzzle = convertDataToPuzzle(puzzleData)
        }
        if (bundle == null || puzzle == null) {
//            Log.d(TAG, "No bundle received!")
            solverDataReceived = false
            Toast.makeText(context, "Invalid puzzle. ", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_solver_steps, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.solver_steps_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(container!!.context)
        val stepCountText = view.findViewById<TextView>(R.id.solver_steps_step_count)
        resetButton = view.findViewById(R.id.solver_steps_reset)
        if (solverDataReceived && puzzle != null) {
            val puzzleSolver = PuzzleSolver(puzzle!!)
            val solverStepsAdapter = SolverStepsAdapter(puzzleSolver.solution()!!)
//            Log.d(TAG, "Puzzle solver solution: ${puzzleSolver.solution()}")
            val steps = (solverStepsAdapter.itemCount - 1).toString().toInt()
            // subtract one, because the initial board is counted
            stepCountText.text = getString(R.string.steps, steps)
            recyclerView.adapter = solverStepsAdapter
        }
        return view
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        resetButton.setOnClickListener { // move back to solver page
            val fragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_container, SolverFragment())
            fragmentTransaction.commit()
        }
    }

    /**
     * @param puzzleData the String puzzleData received from SolverFragment
     * @return an instance of Puzzle.java from the given data, null if puzzle is invalid
     */
    private fun convertDataToPuzzle(puzzleData: String?): Puzzle? {
        if (puzzleData == null) {
            return null
        }
        if (puzzleData.length != LENGTH * LENGTH) {
            return null
        }
        val tiles = Array(
            LENGTH
        ) { IntArray(LENGTH) }
        var index = 0
        for (i in 0 until LENGTH) {
            for (j in 0 until LENGTH) {
                tiles[i][j] = puzzleData[index++].toString().toInt()
            }
        }
//        Log.d(TAG, tiles.contentDeepToString())
        val puzzle = Puzzle(tiles)
        return if (puzzle.isSolvable()) {
            puzzle
        } else {
            null
        }
    }
}