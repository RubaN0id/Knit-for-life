package com.example.camera

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import di.CameraComponentProvider
import org.junit.Rule
import org.junit.Test

class CameraTest {
//    @Rule
//    @JvmField
//    var activityRule: ActivityScenarioRule<CameraComponentProvider> =
//        ActivityScenarioRule(CameraComponentProvider::class.java)
//
//    @Test
//    fun testButtonClick() {
//
//        fun getActivity() = activityRule.scenario.onActivity {
//
//            Espresso.onView(ViewMatchers.withId(R.id.toCameraButton))
//                .perform(ViewActions.click())
//            Espresso.onView(ViewMatchers.withId(R.id.take_color_button)).check(
//                ViewAssertions.matches(
//                    ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)
//            ))
//            Espresso.onView(ViewMatchers.withId(R.id.tv_color)).check(
//                ViewAssertions.matches(
//                    ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)
//            ))
//            Espresso.onView(ViewMatchers.withId(R.id.take_color_button))
//                .perform(ViewActions.click())
//
//            Espresso.onView(ViewMatchers.withText("Color saved!"))
//                .inRoot(RootMatchers.withDecorView(not(it.getWindow().getDecorView()))).check(
//                ViewAssertions.matches(ViewMatchers.isDisplayed())
//            )
//        }
//    }
}