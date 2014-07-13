package com.elpassion.vielengames.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.api.VielenGamesClient;
import com.elpassion.vielengames.data.SessionRequest;
import com.elpassion.vielengames.event.SessionCreateFailedEvent;
import com.elpassion.vielengames.event.SessionStartedResponseEvent;
import com.elpassion.vielengames.event.bus.EventBus;
import com.elpassion.vielengames.utils.ViewUtils;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.LoginButton;

import javax.inject.Inject;

public final class LoginActivity extends BaseActivity {

    @Inject
    VielenGamesClient client;

    @Inject
    EventBus eventBus;

    private LoginButton facebookLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        eventBus.register(this);
        initFacebookLoginButton();
    }

    private void initFacebookLoginButton() {
        facebookLoginButton = ViewUtils.findView(this, R.id.login_button_facebook);
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
            facebookLoginButton.setVisibility(View.GONE);
            SessionRequest sessionRequest = SessionRequest.builder()
                    .provider("facebook")
                    .providerToken(session.getAccessToken())
                    .build();
            client.createSession(sessionRequest);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    @SuppressWarnings("unused")
    public void onEvent(SessionStartedResponseEvent event) {
        startMainActivity();
        finish();
    }

    @SuppressWarnings("unused")
    public void onEvent(SessionCreateFailedEvent event) {
        facebookLoginButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                facebookLoginButton.setVisibility(View.VISIBLE);
            }
        }, 100L);
        Session.getActiveSession().close();
    }

    public void startMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }
}
