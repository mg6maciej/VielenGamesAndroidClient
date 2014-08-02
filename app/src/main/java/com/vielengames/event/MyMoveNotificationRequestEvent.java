package com.vielengames.event;

import com.vielengames.data.kuridor.KuridorGame;

import lombok.Value;

@Value
public final class MyMoveNotificationRequestEvent {

    KuridorGame game;
}
