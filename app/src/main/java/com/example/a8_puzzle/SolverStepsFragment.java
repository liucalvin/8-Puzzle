package com.example.a8_puzzle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a8_puzzle.solver.Puzzle;
import com.example.a8_puzzle.solver.PuzzleSolver;

import java.util.Arrays;

public class SolverStepsFragment extends Fragment {
    
    private static final String TAG = "SolverStepsFragment";
    private static final int LENGTH = 3;
    private Toolbar toolbar;
    private View view;
    private RecyclerView recyclerView;
    private TextView stepCountText;
    private SolverStepsAdapter solverStepsAdapter;
    private Button resetButton;
    private boolean solverDataReceived;
    private Puzzle puzzle;
    
    public SolverStepsFragment() {
        // Required empty public constructor
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retrieve bundle data from solver
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            solverDataReceived = true;
            String puzzleData = bundle.getString("solverKey");
            Log.d(TAG, "Bundle recieved: " + puzzleData);
            puzzle = convertDataToPuzzle(puzzleData);
        }
        if (bundle == null || puzzle == null) {
            Log.d(TAG, "No bundle recieved!");
            solverDataReceived = false;
            Toast.makeText(getContext(), "Invalid puzzle. ", Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_solver_steps, container, false);
        recyclerView = view.findViewById(R.id.solver_steps_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        stepCountText = view.findViewById(R.id.solver_steps_step_count);
        resetButton = view.findViewById(R.id.solver_steps_reset);
        return view;
    
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // move back to solver page
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new SolverFragment());
                fragmentTransaction.commit();
            }
        });
    
        if (solverDataReceived) {
            PuzzleSolver puzzleSolver = new PuzzleSolver(puzzle);
            solverStepsAdapter = new SolverStepsAdapter(puzzleSolver.solution());
            /*  stepCountText.setText(solverStepsAdapter.getItemCount());*/
            recyclerView.setAdapter(solverStepsAdapter);
        }
    
    
    }
    
    /**
     * @param puzzleData the String puzzleData received from SolverFragment
     * @return an instance of Puzzle.java from the given data, null if puzzle is invalid
     */
    private Puzzle convertDataToPuzzle(String puzzleData) {
        if (puzzleData.length() != LENGTH * LENGTH) {
            return null;
        }
        int[][] tiles = new int[LENGTH][LENGTH];
        int index = 0;
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                tiles[i][j] = Integer.parseInt(String.valueOf(puzzleData.charAt(index++)));
            }
        }
        Log.d(TAG, Arrays.deepToString(tiles));
        Puzzle puzzle = new Puzzle(tiles);
        if (puzzle.isSolvable()) {
            return puzzle;
        } else {
            return null;
        }
    }
    
}