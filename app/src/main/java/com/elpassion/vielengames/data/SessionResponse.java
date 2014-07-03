package com.elpassion.vielengames.data;

import hrisey.Parcelable;
import lombok.Value;
import lombok.experimental.Builder;

@Parcelable
@Value
@Builder
public final class SessionResponse {

    String authToken;
    Player user;
}
