package com.example.cool8puzzle.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    private lateinit var subject: HomeViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        subject = HomeViewModel()
    }

    @Test
    fun `verify reset button resets board and steps`() {
        subject.resetBoard()
        subject.onTileClicked(5)

        assertEquals(1, subject.stepCount.value)

        subject.resetBoard()

        subject.tiles.value.forEachIndexed { index, tile ->
            assertEquals(index + 1, tile)
        }
        assertEquals(0, subject.stepCount.value)
    }

    @Test
    fun `verify scrambling board works correctly`() = runBlocking {
        subject.scrambleBoard()
        val initialTiles = subject.tiles.value

        subject.scrambleBoard()

        val newTiles = subject.tiles.asLiveData().getOrAwaitValue()
//        assertNotEquals(initialTiles, newTiles)
    }

}