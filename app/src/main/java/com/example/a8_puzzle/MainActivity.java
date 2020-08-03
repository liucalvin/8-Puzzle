package com.example.a8_puzzle;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    
    protected Fragment homeFragment, solverFragment, infoFragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        
        // initialize fragments
        homeFragment = new HomeFragment();
        solverFragment = new SolverFragment();
        infoFragment = new InfoFragment();
        replaceFragment(homeFragment);
        
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_nav_home:
                        replaceFragment(homeFragment);
                        return true;
                    case R.id.bottom_nav_solver:
                        replaceFragment(solverFragment);
                        return true;
                    case R.id.bottom_nav_info:
                        replaceFragment(infoFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });
        
  /*      // get solver args
        Intent intent = getIntent();
        String data = intent.getStringExtra("solverData");
        
        // send to solver steps fragment
        Bundle bundle = new Bundle();
        bundle.putString("solverData", data);
        //set args
        SolverStepsFragment solverStepsFragment = new SolverStepsFragment();
        solverStepsFragment.setArguments(bundle);*/
        
    }
    
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();
    }
}