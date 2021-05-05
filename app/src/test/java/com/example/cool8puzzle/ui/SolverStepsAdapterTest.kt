package com.example.cool8puzzle.ui

import android.os.Build
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.test.core.app.ApplicationProvider
import com.example.cool8puzzle.MainApplication
import com.example.cool8puzzle.entity.Puzzle
import com.example.cool8puzzle.ui.viewholders.SolverStepsViewHolder
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class SolverStepsAdapterTest : AutoCloseKoinTest() {

    private lateinit var adapter: SolverStepsAdapter
    private val context = ApplicationProvider.getApplicationContext<MainApplication>()

    @Before
    fun setup() {
        adapter = SolverStepsAdapter()
    }

    @Test
    fun `verify viewholder created correctly`() {
        val viewHolder = adapter.onCreateViewHolder(FrameLayout(context), 0)
        assertTrue(viewHolder is SolverStepsViewHolder)
    }

    @Test
    fun `verify viewholder bound correctly`() {
        adapter.submitList(
            listOf(
                Puzzle(
                    arrayOf(
                        intArrayOf(1, 2, 3),
                        intArrayOf(4, 5, 6),
                        intArrayOf(7, 8, 0)
                    )
                ), Puzzle(
                    arrayOf(
                        intArrayOf(1, 2, 3),
                        intArrayOf(4, 5, 6),
                        intArrayOf(7, 8, 0)
                    )
                )
            )
        )
        val firstViewHolder = adapter.onCreateViewHolder(FrameLayout(context), 0)
        adapter.onBindViewHolder(firstViewHolder, 0)
        assertTrue(firstViewHolder.binding.divider.isVisible)

        val secondViewHolder = adapter.onCreateViewHolder(FrameLayout(context), 0)
        adapter.onBindViewHolder(secondViewHolder, 1)
        assertFalse(secondViewHolder.binding.divider.isVisible)
    }

    @Test
    fun `verify callback methods`() {
        val callback = SolverStepsCallback()
        val puzzle1 = Puzzle(
            arrayOf(
                intArrayOf(1, 2, 3),
                intArrayOf(4, 5, 6),
                intArrayOf(7, 8, 0)
            )
        )
        val puzzle2 = Puzzle(
            arrayOf(
                intArrayOf(1, 2, 3),
                intArrayOf(4, 5, 6),
                intArrayOf(7, 8, 0)
            )
        )

        assertTrue(callback.areItemsTheSame(puzzle1, puzzle1))
        assertFalse(callback.areItemsTheSame(puzzle1, puzzle2))

        assertTrue(callback.areContentsTheSame(puzzle1, puzzle2))
    }

}