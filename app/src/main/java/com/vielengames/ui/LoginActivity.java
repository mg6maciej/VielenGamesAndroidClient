package com.vielengames.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.vielengames.R;
import com.vielengames.api.VielenGamesClient;
import com.vielengames.data.SessionRequest;
import com.vielengames.event.SessionCreateFailedEvent;
import com.vielengames.event.SessionStartedResponseEvent;
import com.vielengames.event.bus.EventBus;
import com.vielengames.ui.login.CreateSessionCallback;
import com.vielengames.ui.login.LoginButtonProvider;
import com.vielengames.utils.ViewUtils;

import javax.inject.Inject;

public final class LoginActivity extends BaseActivity implements CreateSessionCallback {

    @Inject
    VielenGamesClient client;

    @Inject
    EventBus eventBus;

    @Inject
    LoginButtonProvider loginButtonProvider;

    private ViewGroup buttonsContainer;
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
        loginButtonProvider.init(this, buttonsContainer);
    }

    @Override
    public void createSession(String provider, String token) {
        SessionRequest sessionRequest = SessionRequest.builder()
                .provider(provider)
                .providerToken(token)
                .build();
        buttonsContainer.setVisibility(View.INVISIBLE);
        progressIndicator.setVisibility(View.VISIBLE);
        client.createSession(sessionRequest);
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
        loginButtonProvider.close();
    }

    public void startMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButtonProvider.onActivityResult(this, requestCode, resultCode, data);
    }
}
