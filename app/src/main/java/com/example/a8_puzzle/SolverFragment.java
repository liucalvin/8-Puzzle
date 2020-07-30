package com.example.a8_puzzle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class SolverFragment extends Fragment {
    
    private EditText number1, number2, number3, number4, number5, number6, number7, number8, number9;
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
        return view;
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        number1 = view.findViewById(R.id.solver_tile1);
        number2 = view.findViewById(R.id.solver_tile2);
        number3 = view.findViewById(R.id.solver_tile3);
        number4 = view.findViewById(R.id.solver_tile4);
        number5 = view.findViewById(R.id.solver_tile5);
        number6 = view.findViewById(R.id.solver_tile6);
        number7 = view.findViewById(R.id.solver_tile7);
        number8 = view.findViewById(R.id.solver_tile8);
        number9 = view.findViewById(R.id.solver_tile9);
        solveButton = view.findViewById(R.id.solver_solve_button);
        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new SolverStepsFragment());
                fragmentTransaction.commit();
            }
        });
        
        
    }
}