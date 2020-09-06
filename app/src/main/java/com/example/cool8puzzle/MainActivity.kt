package com.example.cool8puzzle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var homeFragment: Fragment
    private lateinit var solverFragment: Fragment
    private lateinit var infoFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // initialize fragments
        homeFragment = HomeFragment()
        solverFragment = SolverFragment()
        infoFragment = InfoFragment()
        replaceFragment(homeFragment)

        // navigate to other fragments using bottom menu
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_home -> {
                    replaceFragment(homeFragment)
                    true
                }
                R.id.bottom_nav_solver -> {
                    replaceFragment(solverFragment)
                    true
                }
                R.id.bottom_nav_info -> {
                    replaceFragment(infoFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction =
            supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_container, fragment)
        fragmentTransaction.commit()
    }
}