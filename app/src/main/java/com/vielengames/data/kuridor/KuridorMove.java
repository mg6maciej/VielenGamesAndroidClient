package com.vielengames.data.kuridor;

import hrisey.Parcelable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

@Parcelable
@Value
public final class KuridorMove {

    public enum MoveType {
        wall,
        pawn
    }

    @Getter(AccessLevel.NONE)
    MoveType moveType;
    String position;

    public boolean isPawn() {
        return moveType == MoveType.pawn;
    }

    public boolean isWall() {
        return moveType == MoveType.wall;
    }

    public static KuridorMove wall(String position) {
        return new KuridorMove(MoveType.wall, position);
    }

    public static KuridorMove pawn(String position) {
        return new KuridorMove(MoveType.pawn, position);
    }
}
