package com.elpassion.vielengames.api;

import android.content.Context;

public interface GooglePlusAuth {

    /* Request code used to invoke sign in user interactions. */
    int RC_SIGN_IN = 0;

    int REQUEST_AUTHORIZATION_CODE = 1;


    String scope1 = "https://www.googleapis.com/auth/userinfo.email";

    String scope2 = "https://www.googleapis.com/auth/userinfo.profile";


    public void connect();

    public void disconnect();

    public void restart();

    public boolean isConnecting();

    public void setSignInButtonClicked(boolean wasClicked);

    public void resolveSignInErrors();

    public void getToken(Context ctx);

    public void requestSignUserOut();

    public boolean isConnected();
}
