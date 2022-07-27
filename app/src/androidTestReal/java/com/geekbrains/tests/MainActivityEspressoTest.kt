package com.geekbrains.tests

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.view.search.MainActivity
import org.hamcrest.CoreMatchers.startsWith
import org.hamcrest.Matcher
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
    fun progressBar_isVisible() {
        onView(withId(R.id.searchEditText))
            .perform(
                replaceText("111"),
                pressImeActionButton())

        onView(withId(R.id.progressBar))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
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

        onView(withId(R.id.searchEditText))
            .perform(replaceText("algol"),
                closeSoftKeyboard())

        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())

        onView(isRoot()).perform(delay())

        onView(withId(R.id.totalCountTextView))
            .check(matches(withText(startsWith("Number of results:"))))
    }

    private fun delay(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $2 seconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(2000)
            }
        }
    }

    @After
    fun close() {
        mainActivityScenario.close()
    }
}
