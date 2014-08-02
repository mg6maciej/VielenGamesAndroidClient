package com.vielengames;

import com.vielengames.data.Player;
import com.vielengames.data.SessionResponse;

import hrisey.Preferences;

@Preferences
public final class VielenGamesPrefs {

    private Player me;
    private String token;
    private boolean helpOverlayAlreadyShown;

    public boolean isSignedIn() {
        return containsToken();
    }

    public void setSignedIn(SessionResponse sessionResponse) {
        setToken(sessionResponse.getAuthToken());
        setMe(sessionResponse.getUser());
    }

    public void clearSignedIn() {
        removeToken();
        removeMe();
    }
}
