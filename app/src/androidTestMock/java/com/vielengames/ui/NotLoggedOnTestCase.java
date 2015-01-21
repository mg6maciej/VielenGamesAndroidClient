package com.vielengames.ui;

import com.vielengames.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public final class NotLoggedOnTestCase extends BaseInstrumentationTestCase {

    public void testNotLoggedOnInitialState() {
        onView(withId(R.id.login_button_stub)).check(matches(isDisplayed()));
    }

    public void testLoggedOnAfterLoginClick() {
        onView(withId(R.id.login_button_stub)).perform(click());
        onView(withId(R.id.main_add_proposal_button)).check(matches(isDisplayed()));
    }
}
