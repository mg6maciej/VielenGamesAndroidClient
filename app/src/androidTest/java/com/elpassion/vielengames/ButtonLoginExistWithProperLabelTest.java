package com.elpassion.vielengames;

import android.view.View;

import com.elpassion.vielengames.ui.LoginActivity;

/**
 * Created by michalsiemionczyk on 16/06/14.
 */
public class ButtonLoginExistWithProperLabelTest extends VielenBaseTestCase<LoginActivity> {


    public ButtonLoginExistWithProperLabelTest() {
        super(LoginActivity.class);
    }

    public void testButtonSignInExist() {
        waitForLoginActivity();
        View but = solo.getView(R.id.sign_in_button);
        assertTrue(but != null);
    }
}
