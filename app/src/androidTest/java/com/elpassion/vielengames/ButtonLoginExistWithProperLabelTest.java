package com.elpassion.vielengames;

import android.widget.Button;

import com.elpassion.vielengames.ui.LoginActivity;

/**
 * Created by michalsiemionczyk on 16/06/14.
 */
public class ButtonLoginExistWithProperLabelTest extends VielenBaseTestCase<LoginActivity> {


    public ButtonLoginExistWithProperLabelTest() {
        super(LoginActivity.class);
    }

    public void testButtonExist(){
        waitForLoginActivity();
        Button but = (Button) solo.getView(R.id.button_login);
        assertTrue(but != null);
    }

    public void testLabelIsProper(){
        waitForLoginActivity();
        Button but = (Button) solo.getView(R.id.button_login);
        assertTrue(but.getText().equals(solo.getString(R.string.button_login_text)));
    }


}
