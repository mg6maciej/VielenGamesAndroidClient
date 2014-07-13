package com.vielengames.event;

import com.vielengames.data.MoveFailure;
import com.vielengames.data.kuridor.KuridorMove;

import lombok.Value;

@Value
public final class MoveFailureEvent {

    MoveFailure.Reason reason;
    KuridorMove move;
}
