package com.vielengames.data;

import hrisey.Parcelable;
import lombok.Value;
import lombok.experimental.Builder;

@Parcelable
@Value
@Builder
public final class SessionRequest {

    String provider;
    String providerToken;
}
