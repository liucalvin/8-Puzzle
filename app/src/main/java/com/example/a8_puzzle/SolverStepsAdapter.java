package com.example.a8_puzzle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a8_puzzle.solver.Puzzle;

import java.util.List;

public class SolverStepsAdapter extends RecyclerView.Adapter<SolverStepsAdapter.ViewHolder> {
    
    private static final String TAG = "SolverStepsAdapter";
    private List<Puzzle> solverSteps;
    private Context context;
    
    public SolverStepsAdapter(List<Puzzle> solverSteps) {
        this.solverSteps = solverSteps;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.solver_list_step, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // recieve data and set it for each position in list
        
        holder.setIsRecyclable(false);      // turn off recycling of views
        holder.setGridView(solverSteps.get(position).getTileList());
        holder.setMovementText(solverSteps.get(position).getMovement());
    }
    
    @Override
    public int getItemCount() {
        return solverSteps.size();
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder {
        
        private TilesAdapter tilesAdapter;
        private View mView;
        private TextView movementText, stepCountText;
        private GridView gridView;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            
            movementText = mView.findViewById(R.id.solver_list_step_movement);
            gridView = mView.findViewById(R.id.solver_list_step_gridview);
        }
        
        protected void setMovementText(String text) {
            movementText.setText(text);
        }
        
        protected void setGridView(List<Tile> singleTileList) {
            tilesAdapter = new TilesAdapter(singleTileList, mView.getContext());
            gridView.setAdapter(tilesAdapter);
        }
    }
}
