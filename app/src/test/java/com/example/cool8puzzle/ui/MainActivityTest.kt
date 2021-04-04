package com.example.cool8puzzle.ui

import android.os.Build
import android.os.Looper
import android.os.Looper.getMainLooper
import androidx.core.view.get
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.example.cool8puzzle.R
import com.example.cool8puzzle.ui.fragments.HomeFragment
import com.example.cool8puzzle.ui.fragments.InfoFragment
import com.example.cool8puzzle.ui.fragments.SolverFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.KoinContextHandler
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainActivityTest: AutoCloseKoinTest() {

    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @After
    fun teardown() {
        if (::activityScenario.isInitialized) {
            activityScenario.close()
        }
    }

    @Test
    fun `verify home fragment is instantiated when activity starts`() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            val fragment = activity.supportFragmentManager.fragments.last()
            assertTrue(fragment is HomeFragment)
        }
    }

    @Test
    fun `verify clicking on home button returns to the initial home fragment`() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            val fragment = activity.supportFragmentManager.fragments.last()
            assertTrue(fragment is HomeFragment)

            activity.binding.bottomNavigation.findViewById<BottomNavigationItemView>(R.id.solverFragment).performClick()

            assertEquals(fragment, activity.supportFragmentManager.fragments.last())
        }
    }

    @Test
    fun `verify navigation to solver fragment`() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            activity.binding.bottomNavigation.findViewById<BottomNavigationItemView>(R.id.solverFragment).performClick()
            shadowOf(getMainLooper()).idle()

            val fragment = activity.supportFragmentManager.fragments.last()
            assertTrue(fragment is SolverFragment)
        }
    }

    @Test
    fun `verify navigation to info fragment`() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            activity.binding.bottomNavigation.findViewById<BottomNavigationItemView>(R.id.infoFragment).performClick()
            shadowOf(getMainLooper()).idle()

            val fragment = activity.supportFragmentManager.fragments.last()
            assertTrue(fragment is InfoFragment)
        }
    }

}