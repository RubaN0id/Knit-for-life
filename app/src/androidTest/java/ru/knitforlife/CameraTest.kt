package ru.knitforlife

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import org.junit.Test

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule

import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not

import org.junit.Rule


class CameraTest {
    @Rule
    @JvmField
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testButtonClick() {

        fun getActivity() = activityRule.scenario.onActivity {

            onView(ViewMatchers.withId(R.id.toCameraButton))
                .perform(ViewActions.click())
            onView(ViewMatchers.withId(R.id.take_color_button)).check(ViewAssertions.matches(
                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)
            ))
            onView(ViewMatchers.withId(R.id.tv_color)).check(ViewAssertions.matches(
                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)
            ))
            onView(ViewMatchers.withId(R.id.take_color_button))
                .perform(ViewActions.click())

            onView(withText("Color saved!")).inRoot(withDecorView(not(it.getWindow().getDecorView()))).check(matches(isDisplayed()))
        }
    }
}