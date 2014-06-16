package com.elpassion.vielengames.api;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.elpassion.vielengames.event.OnAccessTokenReceived;
import com.elpassion.vielengames.event.OnGPlusAuthenticationResponse;
import com.elpassion.vielengames.event.bus.EventBus;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.plus.Plus;

import java.io.IOException;


/**
 * Created by michalsiemionczyk on 16/06/14.
 */
public class GooglePlusAuthImpl implements GooglePlusAuth, ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = GooglePlusAuthImpl.class.getSimpleName();

    private final GoogleApiClient googleApiClient;

    private final EventBus eventBus;

    /*  A flag indicating that a PendingIntent is in progress and prevents
        * us from starting further intents.*/
    private boolean mIntentInProgress;

    private boolean mSignInClicked;

    private ConnectionResult mConnectionResult;


    public GooglePlusAuthImpl(GoogleApiClient googleApiClient, EventBus eventBus) {
        this.googleApiClient = googleApiClient;
        this.eventBus = eventBus;

        this.googleApiClient.registerConnectionCallbacks(this);
        this.googleApiClient.registerConnectionFailedListener(this);

    }

    @Override
    public void connect() {
        googleApiClient.connect();
    }

    @Override
    public void disconnect() {
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void restart() {
        mIntentInProgress = false;
        if (!googleApiClient.isConnecting()) {
            googleApiClient.connect();
        }
    }

    @Override
    public boolean isConnecting() {
        return googleApiClient.isConnecting();
    }

    @Override
    public void setSignInButtonClicked(boolean wasClicked) {
        mSignInClicked = wasClicked;
    }

    @Override
    public void resolveSignInErrors() {
        if (!mIntentInProgress && mConnectionResult != null && mConnectionResult.hasResolution()) {
            mIntentInProgress = true;
            if (mSignInClicked) {
                eventBus.post(OnGPlusAuthenticationResponse.builder()
                        .type(OnGPlusAuthenticationResponse.Type.REQUEST_START_INTENT_SENDER)
                        .connectionResult(mConnectionResult)
                        .build());
            }
        }
    }

    @Override
    public void getToken(final Context context) {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String accessToken;

                String scope = "oauth2:" + scope1 + " " + scope2;
                try {
                    accessToken = GoogleAuthUtil.getToken(context,
                            Plus.AccountApi.getAccountName(googleApiClient),
                            scope);
                } catch (IOException transientEx) {
                    Log.e(TAG, "IOExpcetion Error on getting token : ", transientEx);
                    accessToken = null;
                } catch (UserRecoverableAuthException e) {
                    Log.e(TAG, "UserRecoverableAuthException Error on getting token : ", e);
                    accessToken = null;
                } catch (GoogleAuthException authEx) {
                    Log.e(TAG, "GoogleAuth Error on getting token: ", authEx);
                    accessToken = null;
                } catch (Exception e) {
                    Log.e(TAG, "General exception in getting token:", e);
                    throw new RuntimeException(e);
                }

                return accessToken;
            }

            @Override
            protected void onPostExecute(String token) {
                eventBus.post(new OnAccessTokenReceived(token));
                Log.i(TAG, "Access token retrieved:" + token);
            }
        };

        task.execute(null, null);


    }

    @Override
    public void signUserOut() {
        Plus.AccountApi.clearDefaultAccount(googleApiClient);

        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public boolean isConnected() {
        return googleApiClient.isConnected();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "onConnected");
        mSignInClicked = false;

        eventBus.post(
                OnGPlusAuthenticationResponse.builder()
                        .bundle(bundle)
                        .type(OnGPlusAuthenticationResponse.Type.USER_CONNECTED)
                        .build()
        );
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "connection suspended;");

        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "connection failed;");
        if (!mIntentInProgress) {
            mConnectionResult = connectionResult;
            if (mSignInClicked) {
                resolveSignInErrors();
            }
        }
    }
}
