package com.vielengames.ui;

import com.vielengames.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public final class NotLoggedOnTestCase extends BaseInstrumentationTestCase {

    public void testNotLoggedOnInitialState() {
        onView(withId(R.id.login_buttons_container)).check(matches(isDisplayed()));
    }
}
