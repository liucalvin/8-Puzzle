package com.example.cool8puzzle.ui.fragments

import android.os.Build
import androidx.fragment.app.testing.FragmentScenario
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import com.example.cool8puzzle.R
import com.example.cool8puzzle.ui.MainActivity
import com.example.cool8puzzle.ui.viewmodels.SolverStepsViewModel
import com.example.cool8puzzle.ui.viewmodels.mockSolverStepsViewModel
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class SolverStepsFragmentTest : AutoCloseKoinTest() {

    private lateinit var fragmentScenario: FragmentScenario<SolverStepsFragment>
    private lateinit var viewModel: SolverStepsViewModel

    @Before
    fun setup() {
        viewModel = mockSolverStepsViewModel("123456078")
        loadKoinModules(listOf(module {
            viewModel(override = true) { (_: String) -> viewModel }
        }))
    }

    @After
    fun teardown() {
        if (::fragmentScenario.isInitialized) {
            fragmentScenario.moveToState(Lifecycle.State.DESTROYED)
        }
    }

    @Test
    fun `verify initial ui state`() {
//        launchActivity<MainActivity>().onActivity { activity ->
//            activity.findNavController(R.id.nav_host_fragment_container).apply {
//                navigate(
//                    SolverFragmentDirections.actionSolverFragment2ToSolverStepsFragment(
//                        "123456078"
//                    )
//                )
//            }
//                val navController =
//                    TestNavHostController(ApplicationProvider.getApplicationContext()).apply {
//                        setGraph(R.navigation.nav_graph)
//                    }
//                navController.setCurrentDestination(R.id.solverFragment)
//                navController.navigate(
//                    SolverFragmentDirections.actionSolverFragment2ToSolverStepsFragment(
//                        "123456078"
//                    )
//                )
//
//
//                val navHostFragment = activity.supportFragmentManager.fragments.first() as NavHostFragment
//                val fragment = navHostFragment.childFragmentManager.fragments.first() as SolverStepsFragment
//
//                with(fragment.binding) {
//                    assertEquals(stepCount, 2)
//                }
//            }
//        }
    }
}
