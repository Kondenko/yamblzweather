package com.kondenko.yamblzweather.ui.weather;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.kondenko.yamblzweather.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

/**
 * Created by Mishkun on 25.07.2017.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class UnitsSettingsTest {
    @Rule
    public ActivityTestRule<WeatherActivity> mActivityTestRule = new ActivityTestRule<>(WeatherActivity.class);

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @Test
    public void fahrenheitTest() {
        onView(withId(R.id.settings_button)).perform(click());

        onView(childAtPosition(withId(android.R.id.list), 0)).perform(click());

        onView(allOf(withId(android.R.id.text1), isDisplayed(), withText(R.string.pref_value_unit_fahrenheit))).perform(click());

        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

        onView(withId(R.id.weather_text_temperature)).check(matches(withText(containsString("F"))));
    }

    @Test
    public void kelvinTest() {
        onView(withId(R.id.settings_button)).perform(click());

        onView(childAtPosition(withId(android.R.id.list), 0)).perform(click());

        onView(allOf(withId(android.R.id.text1), isDisplayed(), withText(R.string.pref_value_unit_kelvin))).perform(click());

        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

        onView(withId(R.id.weather_text_temperature)).check(matches(withText(containsString("K"))));
    }

    @Test
    public void celsiusTest() {
        onView(withId(R.id.settings_button)).perform(click());

        onView(childAtPosition(withId(android.R.id.list), 0)).perform(click());

        onView(allOf(withId(android.R.id.text1), isDisplayed(), withText(R.string.pref_value_unit_celsius))).perform(click());

        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

        onView(withId(R.id.weather_text_temperature)).check(matches(withText(containsString("C"))));
    }
}
