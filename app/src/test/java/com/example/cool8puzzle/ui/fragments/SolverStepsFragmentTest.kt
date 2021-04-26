package com.example.cool8puzzle.ui.fragments

import android.os.Build
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import com.example.cool8puzzle.ui.viewmodels.SolverStepsViewModel
import com.example.cool8puzzle.ui.viewmodels.mockHomeViewModel
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
            viewModel(override = true) { viewModel }
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
//        fragmentScenario = launchFragmentInContainer<SolverStepsFragment>().onFragment { fragment ->
//            with(fragment.binding) {
//                assertEquals(stepCount, 2)
//            }
//        }
    }
}
