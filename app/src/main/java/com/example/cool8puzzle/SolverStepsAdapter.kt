package com.example.cool8puzzle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cool8puzzle.solver.Puzzle

class SolverStepsAdapter(private val solverSteps: List<Puzzle>) :
    RecyclerView.Adapter<SolverStepsAdapter.ViewHolder>() {

    companion object {
        private const val TAG = "SolverStepsAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.solver_list_step, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // receive data and set it for each position in list
        holder.setIsRecyclable(false) // turn off recycling of views
        holder.setGridView(solverSteps[position].getTileList())
//        Log.d(TAG, "gridview data: ${solverSteps[position].getTileList()}")
        holder.setMovementText(solverSteps[position].movement)
    }

    override fun getItemCount(): Int {
        return solverSteps.size
    }

    class ViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {
        private val movementText: TextView = mView.findViewById(R.id.solver_list_step_movement)
        private val gridView: GridView = mView.findViewById(R.id.solver_list_step_gridview)
        fun setMovementText(text: String?) {
            movementText.text = text
        }

        fun setGridView(singleTileList: List<Int>) {
            val tilesAdapter = TilesAdapter(singleTileList, mView.context)
//            Log.d(TAG, "TileList: $singleTileList")
            gridView.adapter = tilesAdapter
        }
    }
}