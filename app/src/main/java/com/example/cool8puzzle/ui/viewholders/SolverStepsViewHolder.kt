package com.example.cool8puzzle.ui.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import com.example.cool8puzzle.databinding.ItemSolverStepBinding
import com.example.cool8puzzle.entity.Puzzle
import com.example.cool8puzzle.ui.TilesAdapter

class SolverStepsViewHolder(@VisibleForTesting val binding: ItemSolverStepBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): SolverStepsViewHolder {
            return SolverStepsViewHolder(
                ItemSolverStepBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun bind(item: Puzzle, isLast: Boolean) {
        val adapter = TilesAdapter(binding.root.context)
        binding.gridview.adapter = adapter
        adapter.submitList(item.tileList())
        binding.movement.text = item.movement
        binding.setDivider(isLast)
    }

    private fun ItemSolverStepBinding.setDivider(isLast: Boolean) {
        divider.visibility = if (isLast) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    }
}

