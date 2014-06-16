package com.elpassion.vielengames.event;

import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;

import lombok.Value;
import lombok.experimental.Builder;

/**
 * Created by michalsiemionczyk on 16/06/14.
 */
@Value
@Builder
public class OnGPlusAuthenticationResponse {
    public static enum Type {REQUEST_START_INTENT_SENDER, USER_CONNECTED}

    ;

    Type type;
    Bundle bundle;
    ConnectionResult connectionResult;
}
