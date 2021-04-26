package com.example.cool8puzzle.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cool8puzzle.databinding.ItemSolverStepBinding
import com.example.cool8puzzle.entity.Puzzle

class SolverStepsAdapter : ListAdapter<Puzzle, SolverStepsViewHolder>(SolverStepsCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolverStepsViewHolder {
        return SolverStepsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SolverStepsViewHolder, position: Int) {
        holder.bind(getItem(position), position == itemCount - 1)
    }


}

class SolverStepsCallback : DiffUtil.ItemCallback<Puzzle>() {
    override fun areItemsTheSame(oldItem: Puzzle, newItem: Puzzle): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Puzzle, newItem: Puzzle): Boolean {
        return oldItem.movement == newItem.movement &&
                oldItem.getTileList() == newItem.getTileList()
    }

}

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
        adapter.submitList(item.getTileList())
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

