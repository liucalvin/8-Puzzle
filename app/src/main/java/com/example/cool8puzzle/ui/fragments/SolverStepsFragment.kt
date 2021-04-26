package com.example.cool8puzzle.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cool8puzzle.R
import com.example.cool8puzzle.databinding.FragmentSolverStepsBinding
import com.example.cool8puzzle.entity.Puzzle
import com.example.cool8puzzle.ui.SolverStepsAdapter
import com.example.cool8puzzle.ui.viewmodels.SolverStepsViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import viewBinding

class SolverStepsFragment : Fragment(R.layout.fragment_solver_steps) {

    companion object {
        const val LENGTH = 3
    }

    private lateinit var adapter: SolverStepsAdapter

    @VisibleForTesting
    val binding by viewBinding(FragmentSolverStepsBinding::bind)

    private val viewModel: SolverStepsViewModel by viewModel {
        parametersOf(args.puzzle)
    }

    private val args: SolverStepsFragmentArgs by navArgs()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.reset.setOnClickListener {
            findNavController().navigate(
                SolverStepsFragmentDirections
                    .actionSolverStepsFragmentToSolverFragment()
            )
        }
        adapter = SolverStepsAdapter()
        viewModel.solverSteps.observe(viewLifecycleOwner, ::onSolverDataReceived)
        viewModel.error.observe(viewLifecycleOwner, ::onSolverErrorReceived)
    }

    private fun onSolverErrorReceived(stringResId: Int) {
        Snackbar.make(binding.root, getString(stringResId), Snackbar.LENGTH_INDEFINITE).show()
    }

    private fun onSolverDataReceived(list: List<Puzzle>) = with(binding) {
        progressBar.visibility = View.INVISIBLE
        solverStepsList.apply {
            layoutManager = LinearLayoutManager(root.context)
            adapter = this@SolverStepsFragment.adapter.apply {
                submitList(list)
            }
        }
        stepCount.text = root.context.getString(R.string.steps, list.size - 1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.solverStepsList.adapter = null
    }
}