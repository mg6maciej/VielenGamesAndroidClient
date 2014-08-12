package com.vielengames.data.kuridor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
final class KuridorMoveValidatorImpl {

    private static final Pattern PAWN_MOVE_PATTERN = Pattern.compile("[a-i][1-9]");
    private static final Pattern WALL_MOVE_PATTERN = Pattern.compile("[a-h][1-8][hv]");

    private final KuridorGameState state;
    private final KuridorMove move;

    public boolean isMoveValid() {
        if (state.getActiveTeam() != null) {
            if (move.isPawn()) {
                if (PAWN_MOVE_PATTERN.matcher(move.getPosition()).matches()) {
                    return isPawnMoveValid();
                }
            } else if (move.isWall()) {
                if (WALL_MOVE_PATTERN.matcher(move.getPosition()).matches()) {
                    return isWallMoveValid();
                }
            }
        }
        return false;
    }

    private boolean isPawnMoveValid() {
        return state.getLegalPawnMoves().contains(move);
    }

    private int distanceBetweenPositions(String position1, String position2) {
        return Math.abs(position1.charAt(0) - position2.charAt(0))
                + Math.abs(position1.charAt(1) - position2.charAt(1));
    }

    private boolean isWallMoveValid() {
        if (state.getActiveTeamWallsLeft() == 0) {
            return false;
        }
        Collection<String> walls = state.getWalls();
        String position = move.getPosition();
        if (walls.contains(position)) {
            return false;
        }
        String otherWayPosition = position.substring(0, 2) + (position.charAt(2) == 'h' ? 'v' : 'h');
        if (walls.contains(otherWayPosition)) {
            return false;
        }
        if (position.charAt(2) == 'h') {
            String eastOverlappingPosition = (char) (position.charAt(0) + 1) + position.substring(1);
            String westOverlappingPosition = (char) (position.charAt(0) - 1) + position.substring(1);
            if (walls.contains(eastOverlappingPosition) || walls.contains(westOverlappingPosition)) {
                return false;
            }
        } else {
            String northOverlappingPosition = "" + position.charAt(0) + (char) (position.charAt(1) + 1) + position.charAt(2);
            String southOverlappingPosition = "" + position.charAt(0) + (char) (position.charAt(1) - 1) + position.charAt(2);
            if (walls.contains(northOverlappingPosition) || walls.contains(southOverlappingPosition)) {
                return false;
            }
        }
        Set<String> newWalls = new HashSet<String>(state.getWalls());
        newWalls.add(move.getPosition());
        KuridorGameState newState = state.withWalls(newWalls);
        if (!hasAccessToLine(newState, newState.getTeam1().getPawnPosition(), '9')) {
            return false;
        }
        if (!hasAccessToLine(newState, newState.getTeam2().getPawnPosition(), '1')) {
            return false;
        }
        return true;
    }

    private boolean hasAccessToLine(KuridorGameState state, String pawnPosition, char lineId) {
        Set<String> allAccessiblePositions = new HashSet<String>();
        Set<String> newAccessiblePositions = Collections.singleton(pawnPosition);
        while (newAccessiblePositions.size() > 0) {
            for (String newPosition : newAccessiblePositions) {
                if (newPosition.charAt(1) == lineId) {
                    return true;
                }
            }
            Set<String> newerAccessiblePositions = getAllNeighbours(state, newAccessiblePositions);
            newerAccessiblePositions.removeAll(allAccessiblePositions);
            allAccessiblePositions.addAll(newAccessiblePositions);
            newAccessiblePositions = newerAccessiblePositions;
        }
        return false;
    }

    private Set<String> getAllNeighbours(KuridorGameState state, Set<String> positions) {
        Set<String> neighbours = new HashSet<String>();
        char[] limits = {'9', '1', 'i', 'a'};
        int[] limitsIndices = {1, 1, 0, 0};
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (String position : positions) {
            for (int i = 0; i < limits.length; i++) {
                if (position.charAt(limitsIndices[i]) != limits[i]) {
                    String potentialNeighbour = "" + (char) (position.charAt(0) + directions[i][0]) + (char) (position.charAt(1) + directions[i][1]);
                    if (!isBlockedByWall(state, position, potentialNeighbour)) {
                        neighbours.add(potentialNeighbour);
                    }
                }
            }
        }
        neighbours.removeAll(positions);
        return neighbours;
    }

    private boolean isBlockedByWall(KuridorGameState state, String startPosition, String endPosition) {
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
}
