package com.example.a8_puzzle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class SolverStepsFragment extends Fragment {
    
    private static final String TAG = "SolverStepsFragment";
    private static final int LENGTH = 3;
    private Toolbar toolbar;
    private View view;
    private Button resetButton;
    private boolean solverDataRecieved;
    
    public SolverStepsFragment() {
        // Required empty public constructor
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retrieve bundle data from solver
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            solverDataRecieved = true;
            String puzzleData = bundle.getString("solverKey");
            Log.d(TAG, "Bundle recieved: " + puzzleData);
            
        } else {
            Log.d(TAG, "No bundle recieved!");
            solverDataRecieved = false;
            Toast.makeText(getContext(), "Please fill in the board to use the solver correctly.", Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_solver_steps, container, false);
        
        return view;
        
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        resetButton = view.findViewById(R.id.solver_steps_reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // move back to solver page
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new SolverFragment());
                fragmentTransaction.commit();
            }
        });
    }
    

    
}