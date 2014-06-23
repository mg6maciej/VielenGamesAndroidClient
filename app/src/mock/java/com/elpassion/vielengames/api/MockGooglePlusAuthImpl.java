package com.elpassion.vielengames.api;

import android.content.Context;

public final class MockGooglePlusAuthImpl implements GooglePlusAuth {


    @Override
    public void connect(Context context) {

    }

    @Override
    public void disconnect() {
    }

    @Override
    public void restart() {
    }

    @Override
    public boolean isConnecting() {
        return false;
    }

    @Override
    public void setSignInButtonClicked(boolean wasClicked) {

    }

    @Override
    public void resolveSignInErrors() {
    }

    @Override
    public void getToken(final Context context) {
    }

    @Override
    public void requestSignUserOut(Context context) {

    }


    @Override
    public boolean isConnected() {
        return false;
    }

}
