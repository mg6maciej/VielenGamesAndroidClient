package com.elpassion.vielengames;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

import com.elpassion.vielengames.ui.LoginActivity;
import com.robotium.solo.Solo;

public class VielenBaseTestCase<T extends Activity> extends ActivityInstrumentationTestCase2<T> {

    protected Solo solo;

    public VielenBaseTestCase(Class<T> clazz) {
        super(clazz);
    }

    @Override
    protected void setUp() {

        clearSharedPreferences();
        solo = new Solo(getInstrumentation(), getActivity());
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
            }
        });
    }

    private void clearSharedPreferences() {
        Context context = getInstrumentation().getTargetContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().clear().commit();
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        clearSharedPreferences();
    }

    protected void login() {
        waitForLoginActivity();
        solo.clickOnButton("Login via G+");
    }


    protected boolean waitForLoginActivity() {
        return solo.waitForActivity(LoginActivity.class);
    }


    protected boolean waitForDialog() {
        return solo.waitForDialogToOpen();
    }
}