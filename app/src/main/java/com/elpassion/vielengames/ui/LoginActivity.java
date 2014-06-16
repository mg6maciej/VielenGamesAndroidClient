package com.elpassion.vielengames.ui;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.api.GooglePlusAuth;
import com.elpassion.vielengames.api.VielenGamesClient;
import com.elpassion.vielengames.event.OnAccessTokenReceived;
import com.elpassion.vielengames.event.OnGPlusAuthenticationResponse;
import com.elpassion.vielengames.event.bus.EventBus;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

public final class LoginActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = LoginActivity.class.getSimpleName();
    @Inject
    GooglePlusAuth googlePlusAuth;

    @Inject
    GoogleApiClient googleApiClient;

    @Inject
    VielenGamesClient client;

    @Inject
    EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        googlePlusAuth.connect();
        eventBus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
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
        }

    }

    @SuppressWarnings("unused")
    public void onEvent(OnAccessTokenReceived event) {
        startMainActivity();
    }


    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == GooglePlusAuth.RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                googlePlusAuth.setSignInButtonClicked(false);
            }
            googlePlusAuth.restart();
        }
    }

    public void startMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
