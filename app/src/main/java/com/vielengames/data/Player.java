package com.vielengames.data;

import hrisey.Parcelable;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Builder;

@Parcelable
@Value
@Builder
@EqualsAndHashCode(of = "id")
public final class Player {

    String id;
    String name;
    String avatarUrl;
    Team team;
}
