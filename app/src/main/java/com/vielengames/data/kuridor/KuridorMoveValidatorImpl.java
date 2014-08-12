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
        if (state.isActive()) {
            if (move.isPawn()) {
                if (matchesPawnMovePattern(move.getPosition())) {
                    return isPawnMoveValid();
                }
            } else if (move.isWall()) {
                if (matchesWallMovePattern(move.getPosition())) {
                    return isWallMoveValid();
                }
            }
        }
        return false;
    }

    private boolean matchesPawnMovePattern(String position) {
        return PAWN_MOVE_PATTERN.matcher(position).matches();
    }

    private boolean matchesWallMovePattern(String position) {
        return WALL_MOVE_PATTERN.matcher(position).matches();
    }

    private boolean isPawnMoveValid() {
        return state.getLegalPawnMoves().contains(move);
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
        return hasAccessToLine(newWalls, state.getFirstTeamState().getPawnPosition(), '9')
                && hasAccessToLine(newWalls, state.getSecondTeamState().getPawnPosition(), '1');
    }

    private boolean hasAccessToLine(Collection<String> walls, String pawnPosition, char lineId) {
        Set<String> allAccessiblePositions = new HashSet<String>();
        Set<String> newAccessiblePositions = Collections.singleton(pawnPosition);
        while (newAccessiblePositions.size() > 0) {
            for (String newPosition : newAccessiblePositions) {
                if (newPosition.charAt(1) == lineId) {
                    return true;
                }
            }
            Set<String> newerAccessiblePositions = getAllNeighbours(walls, newAccessiblePositions);
            newerAccessiblePositions.removeAll(allAccessiblePositions);
            allAccessiblePositions.addAll(newAccessiblePositions);
            newAccessiblePositions = newerAccessiblePositions;
        }
        return false;
    }

    private Set<String> getAllNeighbours(Collection<String> walls, Set<String> positions) {
        Set<String> neighbours = new HashSet<String>();
        for (String position : positions) {
            for (KuridorMove.Direction direction : KuridorMove.Direction.values()) {
                String potentialNeighbour = direction.applyToPosition(position);
                if (matchesPawnMovePattern(potentialNeighbour) &&
                        !MoveBlockedChecker.isBlockedByWall(walls, position, potentialNeighbour)) {
                    neighbours.add(potentialNeighbour);
                }
            }
        }
        neighbours.removeAll(positions);
        return neighbours;
    }
}
