package com.example.cool8puzzle.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cool8puzzle.R
import java.util.*

class SolverFragment : Fragment() {
    private var numberTexts: MutableList<EditText> = mutableListOf()
    private lateinit var solveButton: Button
    private lateinit var resetButton: Button

    companion object {
        private const val TAG = "Solver Fragment"
        private const val LENGTH = 3
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        resetNumbers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_solver, container, false)

        // initialize all the tiles
        val number1 = view.findViewById<EditText>(R.id.solver_tile1)
        val number2 = view.findViewById<EditText>(R.id.solver_tile2)
        val number3 = view.findViewById<EditText>(R.id.solver_tile3)
        val number4 = view.findViewById<EditText>(R.id.solver_tile4)
        val number5 = view.findViewById<EditText>(R.id.solver_tile5)
        val number6 = view.findViewById<EditText>(R.id.solver_tile6)
        val number7 = view.findViewById<EditText>(R.id.solver_tile7)
        val number8 = view.findViewById<EditText>(R.id.solver_tile8)
        val number9 = view.findViewById<EditText>(R.id.solver_tile9)
        numberTexts.add(number1)
        numberTexts.add(number2)
        numberTexts.add(number3)
        numberTexts.add(number4)
        numberTexts.add(number5)
        numberTexts.add(number6)
        numberTexts.add(number7)
        numberTexts.add(number8)
        numberTexts.add(number9)
        solveButton = view.findViewById(R.id.solver_solve_button)
        resetButton = view.findViewById(R.id.solver_reset_button)
        return view
    }

    private fun resetNumbers() {
//        Log.d(TAG, "Resetting numbers");
        for (et in numberTexts) {
            et.setText("")
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        solveButton.setOnClickListener {
            // get all the numbers from the puzzle tiles into a string to send
            val solverData = solverData
            if (isValid(solverData)) {
//                    Log.d(TAG, "Solver data valid.");
                // send the solver data
                val bundle = Bundle()
                bundle.putString("solverKey", solverData)
                val solverStepsFragment = SolverStepsFragment()
                solverStepsFragment.arguments = bundle
//                 Log.d(TAG, "Bundle args sent: " + solverData);

                // move to solver steps fragment
//                val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
//                fragmentTransaction.replace(R.id.main_container, solverStepsFragment)
//                fragmentTransaction.commit()
            } else {
                // else solver data is not valid
                Toast.makeText(context, "Please enter the puzzle correctly. ", Toast.LENGTH_SHORT)
                    .show()
//                Log.d(TAG, "solverData: $solverData")
            }
        }
        resetButton.setOnClickListener { resetNumbers() }
    }

    private fun isValid(solverData: String): Boolean {
//        Log.d(TAG, "Solver data: $solverData")
        // must be of correct length, with all numbers from 0-8 included, no repeats
        // (0 has been appended for blank tile when getting solverData)
        val regex = "[0-8]{9}".toRegex()
        if (!regex.matches(solverData)) {
//            Log.d(TAG, "Incorrect length / NaN.")
            return false
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
//                Log.d(TAG, "Repeated/missing numbers.")
                return false
            }
        }
        return true
    }

    // if the text is blank, append 0 for the blank tile
    private val solverData: String
        get() {
            val sb = StringBuilder()
            sb.clear()
//            Log.d(TAG, "Solver data: ${sb.toString()}")
            for (et in numberTexts) {

                // if the text is blank, append 0 for the blank tile
                if (et.text.toString() == "") {
                    sb.append(0)
                } else {
                    sb.append(et.text.toString())
                }
//                Log.d(TAG, "Appending ${et.text.toString()}")
//                Log.d(TAG, "Solver data: ${sb.toString()}")
            }
            if (sb.length > 9) {
                val len = sb.length / 9
                return sb.toString().substring((len - 1) * 9)
            }
            return sb.toString()
        }
}