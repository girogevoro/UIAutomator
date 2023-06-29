package com.geekbrains.tests.view.search


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.geekbrains.tests.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val appCompatEditText = onView(
            allOf(withId(R.id.searchEditText),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    1),
                isDisplayed()))
        appCompatEditText.perform(replaceText("android"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(withId(R.id.searchEditText), withText("android"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    1),
                isDisplayed()))
        appCompatEditText2.perform(pressImeActionButton())

        val materialButton = onView(
            allOf(withId(R.id.searchTotalCountButton), withText("Search total count"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    2),
                isDisplayed()))
        materialButton.perform(click())

        val button = onView(
            allOf(withId(R.id.searchTotalCountButton), withText("SEARCH TOTAL COUNT"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()))
        button.check(matches(isDisplayed()))

        val materialButton2 = onView(
            allOf(withId(R.id.searchTotalCountButton), withText("Search total count"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    2),
                isDisplayed()))
        materialButton2.perform(click())

        val materialButton3 = onView(
            allOf(withId(R.id.toDetailsActivityButton), withText("to details"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    3),
                isDisplayed()))
        materialButton3.perform(click())

        val materialButton4 = onView(
            allOf(withId(R.id.incrementButton), withText("+"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    2),
                isDisplayed()))
        materialButton4.perform(click())

        val button7 = onView(
            allOf(withId(R.id.incrementButton), withText("+"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()))
        button7.check(matches(isDisplayed()))

        val textView = onView(
            allOf(withId(R.id.totalCountTextView), withText("Number of results: 1481144"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()))
        textView.check(matches(withText("Number of results: 1481144")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int,
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
