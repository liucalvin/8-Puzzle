package com.example.cool8puzzle.ui.fragments

import android.os.Build
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.example.cool8puzzle.R
import com.example.cool8puzzle.ui.viewmodels.HomeViewModel
import com.example.cool8puzzle.ui.viewmodels.mockHomeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class HomeFragmentTest : AutoCloseKoinTest() {

    private lateinit var fragmentScenario: FragmentScenario<HomeFragment>
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        homeViewModel = mockHomeViewModel()
        loadKoinModules(listOf(module {
            viewModel(override = true) { mockHomeViewModel() }
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
        fragmentScenario = launchFragmentInContainer<HomeFragment>().onFragment { fragment ->
            assertEquals("Steps: 0", fragment.binding.stepCounter.text)
            assertFalse(fragment.binding.solvedText.isVisible)
            assertTrue(fragment.binding.gridview.isEnabled)
        }
    }

    @Test
    fun `verify board is correctly displayed from viewmodel`() = runBlocking {
        val tileList = listOf(3, 7, 5, 1, 8, 2, 0, 4, 6)
        val tilesFlow = MutableStateFlow(tileList)
        fragmentScenario = launchFragmentInContainer<HomeFragment>().onFragment { fragment ->
            whenever(homeViewModel.tiles).thenReturn(tilesFlow)
            homeViewModel.resetBoard()
            homeViewModel.scrambleBoard()
            fragment.binding.gridview.children.asIterable().forEachIndexed { index, view ->
                assertEquals(
                    view.findViewById<TextView>(R.id.number).text,
                    tileList[index]
                )
            }
        }
    }
}
