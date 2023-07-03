package com.geekbrains.tests.view.details

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.geekbrains.tests.R

@RunWith(AndroidJUnit4::class)
class DetailsFragmentEspressoTest {
    private lateinit var scenario: FragmentScenario<DetailsFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer()
    }

    @Test
    fun fragment_AssertNotNull() {
        scenario.onFragment() {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun activityTextView_IsDisplayed() {
        onView(withId(R.id.totalCountTextView)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun activityTextView_IsCompletelyDisplayed() {
        onView(withId(R.id.totalCountTextView)).check(matches(ViewMatchers.isCompletelyDisplayed()))
    }

    @Test
    fun fragment_testBundle() {
        val fragmentArgs = bundleOf("TOTAL_COUNT_EXTRA" to 10)
        val scenario = launchFragmentInContainer<DetailsFragment>(fragmentArgs)
        scenario.moveToState(Lifecycle.State.RESUMED)
        val assertion = matches(withText("Number of results: 10"))
        onView(withId(R.id.totalCountTextView)).check(assertion)
    }


    @Test
    fun fragment_testSetCountMethod() {
        scenario.onFragment { fragment ->
            fragment.setCount(10)
        }
        val assertion = matches(withText("Number of results: 10"))
        onView(withId(R.id.totalCountTextView)).check(assertion)
    }

    @Test
    fun fragment_testIncrementButton() {
        onView(withId(R.id.incrementButton)).perform(click())
        val TEST_NUMBER_OF_RESULTS_PLUS_1 = "Number of results: 1"
        onView(withId(R.id.totalCountTextView)).check(matches(withText(TEST_NUMBER_OF_RESULTS_PLUS_1)))
    }

    @Test
    fun fragment_testDecrementButton() {
        onView(withId(R.id.decrementButton)).perform(click())
        val TEST_NUMBER_OF_RESULTS_MINUS_1 = "Number of results: -1"
        onView(withId(R.id.totalCountTextView)).check(
            matches(
                withText(
                    TEST_NUMBER_OF_RESULTS_MINUS_1
                )
            )
        )
    }

    @Test
    fun fragmentButtons_AreEffectiveVisible() {
        onView(withId(R.id.incrementButton)).check(
            matches(
                ViewMatchers.withEffectiveVisibility(
                    ViewMatchers.Visibility
                        .VISIBLE
                )
            )
        )
        onView(withId(R.id.decrementButton)).check(
            matches(
                ViewMatchers.withEffectiveVisibility(
                    ViewMatchers.Visibility
                        .VISIBLE
                )
            )
        )
    }

    @After
    fun close() {
        scenario.close()
    }
}