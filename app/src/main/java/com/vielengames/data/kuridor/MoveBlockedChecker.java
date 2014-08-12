package com.vielengames.data.kuridor;

import java.util.Collection;

public final class MoveBlockedChecker {

    private MoveBlockedChecker() {
    }

    public static boolean isBlockedByWall(KuridorGameState state, String startPosition, String endPosition) {
        if (distanceBetweenPositions(startPosition, endPosition) != 1) {
            throw new IllegalArgumentException();
        }
        if (startPosition.compareTo(endPosition) > 0) {
            String tmp = startPosition;
            startPosition = endPosition;
            endPosition = tmp;
        }
        boolean directionX = startPosition.charAt(0) - endPosition.charAt(0) != 0;
        Collection<String> walls = state.getWalls();
        if (directionX) {
            if (walls.contains(startPosition + "v")
                    || walls.contains("" + startPosition.charAt(0) + (char) (startPosition.charAt(1) - 1) + "v")) {
                return true;
            }
        } else {
            if (walls.contains(startPosition + "h")
                    || walls.contains("" + (char) (startPosition.charAt(0) - 1) + startPosition.charAt(1) + "h")) {
                return true;
            }
        }
        return false;
    }

    private static int distanceBetweenPositions(String position1, String position2) {
        return Math.abs(position1.charAt(0) - position2.charAt(0))
                + Math.abs(position1.charAt(1) - position2.charAt(1));
    }
}
