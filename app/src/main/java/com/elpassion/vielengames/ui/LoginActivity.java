package com.elpassion.vielengames.ui;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.api.GooglePlusAuth;
import com.elpassion.vielengames.api.VielenGamesClient;
import com.elpassion.vielengames.data.SessionRequest;
import com.elpassion.vielengames.event.OnAccessTokenReceived;
import com.elpassion.vielengames.event.OnGPlusAuthenticationResponse;
import com.elpassion.vielengames.event.SessionResponseEvent;
import com.elpassion.vielengames.event.bus.EventBus;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;

import javax.inject.Inject;

public final class LoginActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = LoginActivity.class.getSimpleName();


    @Inject
    GooglePlusAuth googlePlusAuth;

    @Inject
    VielenGamesClient client;

    @Inject
    EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideActionBar();
        setContentView(R.layout.login_activity);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        eventBus.register(this);
        googlePlusAuth.connect(this);
        customizeSignInButton();
    }

    private void hideActionBar() {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
    }

    private void customizeSignInButton() {
        SignInButton button = (SignInButton) findViewById(R.id.sign_in_button);
        button.setStyle(SignInButton.SIZE_WIDE, SignInButton.COLOR_LIGHT);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        googlePlusAuth.disconnect();
        eventBus.unregister(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_in_button) {

            if (!googlePlusAuth.isConnecting()) {
                googlePlusAuth.setSignInButtonClicked(true);
                googlePlusAuth.resolveSignInErrors();
            }
        }
    }

    @SuppressWarnings("unused")
    public void onEvent(OnGPlusAuthenticationResponse event) {

        switch (event.getType()) {
            case USER_CONNECTED: {
                Toast.makeText(this, getString(R.string.user_successfully_connected), Toast.LENGTH_SHORT).show();
                Log.i(TAG, "User conncted, get TOken()");
                googlePlusAuth.getToken(this);
                break;
            }
            case REQUEST_START_INTENT_SENDER: {

                ConnectionResult result = event.getConnectionResult();
                try {
                    result.startResolutionForResult(this, GooglePlusAuth.RC_SIGN_IN);
                } catch (IntentSender.SendIntentException e) {
                    googlePlusAuth.restart();
                }
                break;
            }
            case USER_RECOVERABLE_AUTH_REQUEST: {
                startActivityForResult(event.getIntent(), GooglePlusAuth.REQUEST_AUTHORIZATION_CODE);

            }
        }

    }

    @SuppressWarnings("unused")
    public void onEvent(OnAccessTokenReceived event) {
        Log.i(TAG, "onAccessTOkenReceived:" + event.getToken());


        SessionRequest sessionRequest = SessionRequest.builder()
                .provider("google")
                .providerToken(event.getToken())
                .build();
        client.createSession(sessionRequest);


    }

    @SuppressWarnings("unused")
    public void onEvent(SessionResponseEvent event) {
        startMainActivity();
        finish();
    }


    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == GooglePlusAuth.RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                googlePlusAuth.setSignInButtonClicked(false);
            }
            googlePlusAuth.restart();
        } else if (requestCode == GooglePlusAuth.REQUEST_AUTHORIZATION_CODE) {
            if (responseCode == RESULT_OK) {
                Log.i(TAG, "came back from authorization, getToken()");
                googlePlusAuth.getToken(this);
            }
        }
    }

    public void startMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
