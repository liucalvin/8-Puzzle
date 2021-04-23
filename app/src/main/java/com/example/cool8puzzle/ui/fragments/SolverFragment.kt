package com.example.cool8puzzle.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.cool8puzzle.R
import com.example.cool8puzzle.databinding.FragmentSolverBinding
import com.example.cool8puzzle.ui.viewmodels.SolverViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import viewBinding
import java.lang.StringBuilder

class SolverFragment : Fragment(R.layout.fragment_solver) {

    @VisibleForTesting
    val binding by viewBinding(FragmentSolverBinding::bind)
    private val viewModel: SolverViewModel by viewModel()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.solveButton.setOnClickListener {
            viewModel.submitPuzzle(solverData)
        }

        binding.resetButton.setOnClickListener {
            resetNumbers()
        }

        viewModel.onPuzzleValidated().asLiveData().observe(viewLifecycleOwner) { response ->
            when (response) {
                SolverViewModel.ResponseState.SUCCESS -> onPuzzleSuccess()
                SolverViewModel.ResponseState.SYNTAX_ERROR -> showError(getString(R.string.solver_input_error_syntax))
                SolverViewModel.ResponseState.FORMAT_ERROR -> showError(getString(R.string.solver_input_error_repeats))
                else -> { /* no-op */
                }
            }
        }
    }

    private fun showError(errorMessage: String) =
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()


    private fun onPuzzleSuccess() {
        SolverFragmentDirections.actionSolverFragment2ToSolverStepsFragment(solverData).apply {
            findNavController().navigate(this)
        }
    }

    private val numberInputs by lazy {
        with(binding) {
            listOf(
                solverTile1,
                solverTile2,
                solverTile3,
                solverTile4,
                solverTile5,
                solverTile6,
                solverTile7,
                solverTile8,
                solverTile9,
            )
        }
    }

    private fun resetNumbers() {
        numberInputs.forEach {
            it.setText("")
        }
    }

    private val solverData: String
        get() {
            val data = StringBuilder()
            numberInputs.forEach { input ->
                with(input.text.toString()) {
                    if (this == "") {
                        data.append("0")     // append 0 if blank
                    } else {
                        data.append(this.first().toString())
                    }
                }
            }
            Log.d("^^^", data.toString())
            return data.toString()
        }
}

