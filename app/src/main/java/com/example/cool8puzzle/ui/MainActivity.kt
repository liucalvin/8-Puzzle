package com.example.cool8puzzle.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.cool8puzzle.R
import com.example.cool8puzzle.databinding.ActivityMainBinding
import com.example.cool8puzzle.ui.fragments.HomeFragment
import com.example.cool8puzzle.ui.fragments.InfoFragment
import com.example.cool8puzzle.ui.fragments.SolverFragment

class MainActivity : AppCompatActivity() {

    @VisibleForTesting
    val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val infoFragment = InfoFragment()
        val solverFragment = SolverFragment()
        val homeFragment = HomeFragment()

        replaceFragment(homeFragment)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.infoFragment -> replaceFragment(infoFragment)
                R.id.solverFragment -> replaceFragment(solverFragment)
                R.id.homeFragment -> replaceFragment(homeFragment)
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment_container, fragment)
            commit()
        }
    }

}

// from https://zhuinden.medium.com/simple-one-liner-viewbinding-in-fragments-and-activities-with
// -kotlin-961430c6c07c
inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) = lazy(LazyThreadSafetyMode.NONE) {
    bindingInflater.invoke(layoutInflater)
}