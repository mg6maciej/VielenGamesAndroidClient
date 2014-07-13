package com.vielengames.event;

import com.vielengames.data.SessionResponse;

import lombok.Value;

@Value
public final class SessionStartedResponseEvent {

    SessionResponse sessionResponse;
}
