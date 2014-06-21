package com.elpassion.vielengames.data.kuridor;

import java.util.Collection;
import java.util.regex.Pattern;

final class KuridorMoveValidatorImpl {

    private static final Pattern PAWN_MOVE_PATTERN = Pattern.compile("[a-i][1-9]");

    private KuridorMoveValidatorImpl() {
    }

    public static boolean isMoveValid(KuridorGameState state, KuridorMove move) {
        if (move.getMoveType() == KuridorMove.MoveType.pawn) {
            if (PAWN_MOVE_PATTERN.matcher(move.getPosition()).matches()) {
                return isPawnMoveValid(state, move);
            }
        }
        return false;
    }

    private static boolean isPawnMoveValid(KuridorGameState state, KuridorMove move) {
        if (state.getInactiveTeamsPawnPositions().contains(move.getPosition())) {
            return false;
        }
        String activePawnPosition = state.getActiveTeamPawnPosition();
        int distance = distanceBetweenPositions(activePawnPosition, move.getPosition());
        if (distance == 1) {
            int directionX = move.getPosition().charAt(0) - activePawnPosition.charAt(0);
            int directionY = move.getPosition().charAt(1) - activePawnPosition.charAt(1);
            String[] blockingWalls;
            if (directionY == 1) {
                blockingWalls = new String[]{
                        activePawnPosition + "h",
                        "" + (char) (activePawnPosition.charAt(0) - 1) + activePawnPosition.charAt(1) + "h"
                };
            } else if (directionY == -1) {
                blockingWalls = new String[]{
                        move.getPosition() + "h",
                        "" + (char) (move.getPosition().charAt(0) - 1) + move.getPosition().charAt(1) + "h"
                };
            } else if (directionX == 1) {
                blockingWalls = new String[]{
                        activePawnPosition + "v",
                        "" + activePawnPosition.charAt(0) + (char) (activePawnPosition.charAt(1) - 1) + "v"
                };
            } else {
                blockingWalls = new String[]{
                        move.getPosition() + "v",
                        "" + move.getPosition().charAt(0) + (char) (move.getPosition().charAt(1) - 1) + "v"
                };
            }
            Collection<String> walls = state.getWalls();
            for (String blockingWall : blockingWalls) {
                if (walls.contains(blockingWall)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private static int distanceBetweenPositions(String position1, String position2) {
        return Math.abs(position1.charAt(0) - position2.charAt(0))
                + Math.abs(position1.charAt(1) - position2.charAt(1));
    }

}
