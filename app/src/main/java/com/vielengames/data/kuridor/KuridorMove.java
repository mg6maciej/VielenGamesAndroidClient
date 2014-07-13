package com.vielengames.data.kuridor;

import hrisey.Parcelable;
import lombok.Value;

@Parcelable
@Value
public final class KuridorMove {

    public enum MoveType {
        wall,
        pawn
    }

    MoveType moveType;
    String position;

    public static KuridorMove wall(String position) {
        return new KuridorMove(MoveType.wall, position);
    }

    public static KuridorMove pawn(String position) {
        return new KuridorMove(MoveType.pawn, position);
    }
}
