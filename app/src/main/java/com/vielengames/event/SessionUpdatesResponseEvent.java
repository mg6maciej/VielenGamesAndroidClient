package com.vielengames.event;

import com.vielengames.data.Updates;

import lombok.Value;

/**
 * Created by pawel on 16.06.14.
 */
@Value
public class SessionUpdatesResponseEvent {
    Updates updates;
}
