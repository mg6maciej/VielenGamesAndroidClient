package com.elpassion.vielengames.event;

import com.elpassion.vielengames.data.MoveFailure;
import com.elpassion.vielengames.data.kuridor.KuridorMove;

import lombok.Value;

@Value
public final class MoveFailureEvent {

    MoveFailure.Reason reason;
    KuridorMove move;
}
