package com.vielengames.ui;

import android.test.ActivityInstrumentationTestCase2;

public abstract class BaseInstrumentationTestCase extends ActivityInstrumentationTestCase2<LauncherActivity> {

    public BaseInstrumentationTestCase() {
        super(LauncherActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }
}
