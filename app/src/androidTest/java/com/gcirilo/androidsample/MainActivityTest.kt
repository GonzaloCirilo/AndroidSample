package com.gcirilo.androidsample

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gcirilo.androidsample.ui.adapter.UserAdapter
import org.hamcrest.Matcher

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var mActivityTestRule = activityScenarioRule<MainActivity>()

    @Test
    fun filterHasResultsTest() {
        Thread.sleep(500)

        onView(withId(R.id.filterEditText)).perform(typeText(FILTER_QUERY), closeSoftKeyboard())

        Thread.sleep(500)

        val recyclerView = onView(
            allOf(
                withId(R.id.usersRecyclerView),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        recyclerView.check(matches(isDisplayed()))
    }

    @Test
    fun filterHasNoResultsTest() {
        Thread.sleep(500)

        onView(withId(R.id.filterEditText)).perform(typeText(BAD_FILTER_QUERY), closeSoftKeyboard())

        Thread.sleep(500)

        val recyclerView = onView(
            allOf(
                withId(R.id.emptyView),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        recyclerView.check(matches(isDisplayed()))
    }

    @Test
    fun showPublicationsTest() {
        Thread.sleep(500)
        val recyclerView = onView(
            allOf(
                withId(R.id.usersRecyclerView),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        recyclerView.perform(RecyclerViewActions.actionOnItemAtPosition<UserAdapter.ViewHolder>(0, object :
            ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String = "Click on a element of recycler view item"

            override fun perform(uiController: UiController?, view: View?) {
                val v: View? = view?.findViewById(R.id.showPostsButton)
                v?.performClick()
            }

        }))

        Thread.sleep(1500)

        val postRecyclerView = onView(withId(R.id.postsRecyclerView))
        postRecyclerView.check(matches(isDisplayed()))
    }

    companion object {
        private const val FILTER_QUERY = "Leanne"
        private const val BAD_FILTER_QUERY = "ZYX"

    }
}
