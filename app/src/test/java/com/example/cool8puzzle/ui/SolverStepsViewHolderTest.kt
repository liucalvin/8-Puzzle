package com.example.cool8puzzle.ui

import android.os.Build
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.test.core.app.ApplicationProvider
import com.example.cool8puzzle.MainApplication
import com.example.cool8puzzle.entity.Puzzle
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class SolverStepsViewHolderTest: AutoCloseKoinTest() {

    private lateinit var subject: SolverStepsViewHolder
    private val context = ApplicationProvider.getApplicationContext<MainApplication>()

    @Before
    fun setup() {
        subject = SolverStepsViewHolder.from(FrameLayout(context))
    }

    @Test
    fun `verify viewholder bound correctly to item`() {
        val item = Puzzle(
            arrayOf(
                intArrayOf(1, 2, 3),
                intArrayOf(4, 5, 6),
                intArrayOf(7, 8, 0)
            )
        ).apply {
            movement = "movement"
        }
        subject.bind(item, false)
        with(subject.binding) {
            assertEquals("movement", movement.text)
            assertEquals(9, gridview.adapter.count)
            assertTrue(divider.isVisible)
        }
    }

    @Test
    fun `verify divider hidden if viewholder is last in list`() {
        val item = Puzzle(
            arrayOf(
                intArrayOf(1, 2, 3),
                intArrayOf(4, 5, 6),
                intArrayOf(7, 8, 0)
            )
        ).apply {
            movement = "movement"
        }
        subject.bind(item, true)
        with(subject.binding) {
            assertFalse(divider.isVisible)
        }
    }

}