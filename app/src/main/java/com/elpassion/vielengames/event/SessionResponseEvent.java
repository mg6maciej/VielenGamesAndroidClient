package com.elpassion.vielengames.event;

import com.elpassion.vielengames.data.SessionResponse;

import lombok.Value;

@Value
public final class SessionResponseEvent {

    SessionResponse sessionResponse;
}
