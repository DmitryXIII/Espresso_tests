package com.geekbrains.tests

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.view.search.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    private lateinit var mainActivityScenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        mainActivityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun toDetailsButton_Text_Checking() {
        onView(withId(R.id.toDetailsActivityButton)).check(matches(withText("TO DETAILS")))
    }

    @Test
    fun searchEditText_Hint_Checking() {
        val view = onView(withId(R.id.searchEditText))
        val matcher = matches(withHint("Enter keyword e.g. android"))

        view.check(matcher)
    }

    @Test
    fun progressBar_isInvisible() {
        onView(withId(R.id.progressBar))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun navigateToDetails_Test() {
        onView(withId(R.id.toDetailsActivityButton))
            .perform(click())

        onView(withId(R.id.incrementButton)).check(matches(isDisplayed()))
    }

    @Test
    fun activitySearch_IsWorking() {
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText("algol"), closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())
        onView(withId(R.id.totalCountTextView)).check(matches(withText("Number of results: 42")))
    }

    @After
    fun close() {
        mainActivityScenario.close()
    }
}
