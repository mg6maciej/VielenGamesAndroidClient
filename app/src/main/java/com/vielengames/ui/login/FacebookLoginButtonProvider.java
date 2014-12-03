package com.vielengames.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.LoginButton;
import com.vielengames.R;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class FacebookLoginButtonProvider implements LoginButtonProvider {

    private CreateSessionCallback callback;

    @Override
    public void init(CreateSessionCallback callback, ViewGroup buttonsContainer) {
        this.callback = callback;
        LayoutInflater inflater = LayoutInflater.from(buttonsContainer.getContext());
        LoginButton facebookLoginButton = (LoginButton) inflater.inflate(R.layout.login_button_facebook, buttonsContainer, false);
        buttonsContainer.addView(facebookLoginButton);
        facebookLoginButton.setSessionStatusCallback(new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                maybeCreateSessionWithFacebook(session);
            }
        });
        maybeCreateSessionWithFacebook(Session.getActiveSession());
    }

    private void maybeCreateSessionWithFacebook(Session session) {
        if (session != null && session.isOpened()) {
            callback.createSession("facebook", session.getAccessToken());
        }
    }

    @Override
    public void close() {
        Session.getActiveSession().close();
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        Session.getActiveSession().onActivityResult(activity, requestCode, resultCode, data);
    }
}
