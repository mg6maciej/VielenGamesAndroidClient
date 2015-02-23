package com.vielengames.ui;

import android.content.Context;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;

public abstract class BaseInstrumentationTestCase extends ActivityInstrumentationTestCase2<LauncherActivity> {

    public BaseInstrumentationTestCase() {
        super(LauncherActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        clearSharedPrefs();
        getActivity();
    }

    private void clearSharedPrefs() {
        Context context = getInstrumentation().getTargetContext();
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .clear()
                .commit();
    }
}
