package com.example.cool8puzzle.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.cool8puzzle.entity.Puzzle
import com.example.cool8puzzle.ui.viewholders.SolverStepsViewHolder

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
                oldItem.tileList() == newItem.tileList()
    }

}
