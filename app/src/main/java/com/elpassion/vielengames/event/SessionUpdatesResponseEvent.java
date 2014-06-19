package com.elpassion.vielengames.event;

import com.elpassion.vielengames.data.Updates;

import lombok.Value;

/**
 * Created by pawel on 16.06.14.
 */
@Value
public class SessionUpdatesResponseEvent {
    Updates updates;
}
