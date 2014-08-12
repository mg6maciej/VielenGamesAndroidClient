package com.vielengames.data.kuridor;

import hrisey.Parcelable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Parcelable
@Value
public final class KuridorMove {

    public enum MoveType {
        wall,
        pawn
    }

    @RequiredArgsConstructor
    public enum Direction {
        LEFT(-1, 0),
        RIGHT(1, 0),
        UP(0, 1),
        DOWN(0, -1);

        private static final Direction[] LEFT_RIGHT = new Direction[]{LEFT, RIGHT};
        private static final Direction[] UP_DOWN = new Direction[]{UP, DOWN};

        private final int fileDiff;
        private final int rankDiff;

        public String applyToPosition(String position) {
            return "" + (char) (position.charAt(0) + fileDiff)
                    + (char) (position.charAt(1) + rankDiff);
        }

        public Direction[] getSideJumpDirections() {
            return this == LEFT || this == RIGHT ? UP_DOWN : LEFT_RIGHT;
        }
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
