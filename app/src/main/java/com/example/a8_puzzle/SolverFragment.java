package com.example.a8_puzzle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolverFragment extends Fragment {
    
    private static final String TAG = "Solver Fragment";
    private static final int LENGTH = 3;
    private EditText number1, number2, number3, number4, number5, number6, number7, number8, number9;
    private List<EditText> numberTexts;
    private Button solveButton;
    private View view;
    
    public SolverFragment() {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_solver, container, false);
        number1 = view.findViewById(R.id.solver_tile1);
        number2 = view.findViewById(R.id.solver_tile2);
        number3 = view.findViewById(R.id.solver_tile3);
        number4 = view.findViewById(R.id.solver_tile4);
        number5 = view.findViewById(R.id.solver_tile5);
        number6 = view.findViewById(R.id.solver_tile6);
        number7 = view.findViewById(R.id.solver_tile7);
        number8 = view.findViewById(R.id.solver_tile8);
        number9 = view.findViewById(R.id.solver_tile9);
        numberTexts = new ArrayList<>();
        numberTexts.add(number1);
        numberTexts.add(number2);
        numberTexts.add(number3);
        numberTexts.add(number4);
        numberTexts.add(number5);
        numberTexts.add(number6);
        numberTexts.add(number7);
        numberTexts.add(number8);
        numberTexts.add(number9);
    
        solveButton = view.findViewById(R.id.solver_solve_button);
        resetNumbers();
        return view;
    }
    
    private void resetNumbers() {
        for (EditText et : numberTexts) {
            et.setText("");
        }
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String solverData = getSolverData();
                
                if (isValid(solverData)) {
                    Log.d(TAG, "Solver data valid.");
                    // send the solver data
                    Bundle bundle = new Bundle();
                    bundle.putString("solverKey", solverData);
                    
                    SolverStepsFragment solverStepsFragment = new SolverStepsFragment();
                    solverStepsFragment.setArguments(bundle);
                    Log.d(TAG, "Bundle args sent: " + solverData);
                    
                    // move to solver steps fragment
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, solverStepsFragment);
                    fragmentTransaction.commit();
                } else {
                    Toast.makeText(getContext(), "Please enter the puzzle correctly. ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        
    }
    
    private boolean isValid(String solverData) {
        if (solverData.length() != LENGTH * LENGTH && solverData.matches("[0-9]")) {
            Log.d(TAG, "Incorrect length / NaN.");
            return false;
        }
        int[] numbers = new int[LENGTH * LENGTH];
        for (int i = 0; i < LENGTH * LENGTH; i++) {
            numbers[i] = Integer.parseInt(String.valueOf(solverData.charAt(i)));
        }
        Arrays.sort(numbers);
        for (int i = 0; i < LENGTH * LENGTH; i++) {
            /*StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 9; j++) {
                sb.append(numbers[i]);
            }
            Log.d(TAG, "sorted numbers: " + sb.toString());
            */
            if (numbers[i] != i) {
                Log.d(TAG, "Repeated numbers.");
                return false;
            }
        }
        return true;
    }
    
    private String getSolverData() {
        StringBuilder sb = new StringBuilder();
        for (EditText et : numberTexts) {
            // if the text is blank, append 0 for the blank tile
            if (et.getText().toString().equals("")) {
                sb.append(0);
            } else {
                sb.append(et.getText().toString());
            }
        }
        
        return sb.toString();
    }
}