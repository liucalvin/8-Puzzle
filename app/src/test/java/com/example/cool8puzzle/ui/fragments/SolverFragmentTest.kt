package com.example.cool8puzzle.ui.fragments

import android.os.Build
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import com.example.cool8puzzle.R
import junit.framework.Assert.*
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class SolverFragmentTest : AutoCloseKoinTest() {

    private lateinit var fragmentScenario: FragmentScenario<SolverFragment>

    @After
    fun teardown() {
        if (::fragmentScenario.isInitialized) {
            fragmentScenario.moveToState(Lifecycle.State.DESTROYED)
        }
    }

    @Test
    fun `verify initial ui state`() {
        fragmentScenario = launchFragmentInContainer<SolverFragment>().onFragment { fragment ->
            with(fragment.binding) {
                assertEquals(
                    solverInstructions.text,
                    root.context.getString(R.string.solver_instructions)
                )
                assertTrue(solveButton.isEnabled)
                assertTrue(resetButton.isEnabled)
                assertEquals(solverTile1.hint, root.context.getString(R.string.solver_input_hint))
            }
        }
    }

    @Test
    fun `verify pressing reset clears input fields`() {
        fragmentScenario = launchFragmentInContainer<SolverFragment>().onFragment { fragment ->
            with(fragment.binding) {
                solverTile1.setText("1")
                solverTile2.setText("2")
                solverTile4.setText("4")
                solverTile6.setText("6")
                solverTile7.setText("7")
                assertFalse(solverTile1.text.isBlank())
                assertFalse(solverTile2.text.isBlank())
                assertFalse(solverTile4.text.isBlank())
                assertFalse(solverTile6.text.isBlank())
                assertFalse(solverTile7.text.isBlank())

                resetButton.performClick()

                assertTrue(solverTile1.text.isBlank())
                assertTrue(solverTile2.text.isBlank())
                assertTrue(solverTile4.text.isBlank())
                assertTrue(solverTile6.text.isBlank())
                assertTrue(solverTile7.text.isBlank())
            }
        }
    }

}