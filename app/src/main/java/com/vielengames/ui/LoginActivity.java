package com.vielengames.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.elpassion.vielengames.R;
import com.vielengames.api.VielenGamesClient;
import com.vielengames.data.SessionRequest;
import com.vielengames.event.SessionCreateFailedEvent;
import com.vielengames.event.SessionStartedResponseEvent;
import com.vielengames.event.bus.EventBus;
import com.vielengames.utils.ViewUtils;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.LoginButton;

import javax.inject.Inject;

public final class LoginActivity extends BaseActivity {

    @Inject
    VielenGamesClient client;

    @Inject
    EventBus eventBus;

    private View buttonsContainer;
    private ProgressBar progressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        eventBus.register(this);
        initViews();
    }

    private void initViews() {
        buttonsContainer = ViewUtils.findView(this, R.id.login_buttons_container);
        progressIndicator = ViewUtils.findView(this, R.id.login_progress_indicator);
        initFacebookLoginButton();
    }

    private void initFacebookLoginButton() {
        LoginButton facebookLoginButton = ViewUtils.findView(this, R.id.login_button_facebook);
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
            buttonsContainer.setVisibility(View.INVISIBLE);
            progressIndicator.setVisibility(View.VISIBLE);
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
        buttonsContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                buttonsContainer.setVisibility(View.VISIBLE);
                progressIndicator.setVisibility(View.INVISIBLE);
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
