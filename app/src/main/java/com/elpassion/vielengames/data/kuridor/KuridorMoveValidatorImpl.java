package com.elpassion.vielengames.data.kuridor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

final class KuridorMoveValidatorImpl {

    private static final Pattern PAWN_MOVE_PATTERN = Pattern.compile("[a-i][1-9]");
    private static final Pattern WALL_MOVE_PATTERN = Pattern.compile("[a-h][1-8][hv]");

    private KuridorMoveValidatorImpl() {
    }

    public static boolean isMoveValid(KuridorGameState state, KuridorMove move) {
        if (state.getActiveTeam() != null) {
            if (move.getMoveType() == KuridorMove.MoveType.pawn) {
                if (PAWN_MOVE_PATTERN.matcher(move.getPosition()).matches()) {
                    return isPawnMoveValid(state, move);
                }
            } else if (move.getMoveType() == KuridorMove.MoveType.wall) {
                if (WALL_MOVE_PATTERN.matcher(move.getPosition()).matches()) {
                    return isWallMoveValid(state, move);
                }
            }
        }
        return false;
    }

    private static boolean isPawnMoveValid(KuridorGameState state, KuridorMove move) {
        Collection<String> otherPawns = state.getInactiveTeamsPawnPositions();
        if (otherPawns.contains(move.getPosition())) {
            return false;
        }
        String activePawnPosition = state.getActiveTeamPawnPosition();
        int distance = distanceBetweenPositions(activePawnPosition, move.getPosition());
        if (distance == 1) {
            Collection<String> walls = state.getWalls();
            String[] blockingWalls = getBlockingWalls(move.getPosition(), activePawnPosition);
            for (String blockingWall : blockingWalls) {
                if (walls.contains(blockingWall)) {
                    return false;
                }
            }
            return true;
        } else if (distance == 2) {
            int directionX = move.getPosition().charAt(0) - activePawnPosition.charAt(0);
            int directionY = move.getPosition().charAt(1) - activePawnPosition.charAt(1);
            String[] expectedOpponentPawns;
            String[] twoSquares = new String[]{move.getPosition()};
            boolean straight = true;
            if (directionY == 2) {
                expectedOpponentPawns = new String[]{"" + activePawnPosition.charAt(0) + (char) (activePawnPosition.charAt(1) + 1)};
            } else if (directionY == -2) {
                expectedOpponentPawns = new String[]{"" + activePawnPosition.charAt(0) + (char) (activePawnPosition.charAt(1) - 1)};
            } else if (directionX == 2) {
                expectedOpponentPawns = new String[]{"" + (char) (activePawnPosition.charAt(0) + 1) + activePawnPosition.charAt(1)};
            } else if (directionX == -2) {
                expectedOpponentPawns = new String[]{"" + (char) (activePawnPosition.charAt(0) - 1) + activePawnPosition.charAt(1)};
            } else {
                expectedOpponentPawns = new String[]{
                        "" + activePawnPosition.charAt(0) + (char) (activePawnPosition.charAt(1) + directionY),
                        "" + (char) (activePawnPosition.charAt(0) + directionX) + activePawnPosition.charAt(1)
                };
                twoSquares = new String[]{
                        "" + activePawnPosition.charAt(0) + (char) (activePawnPosition.charAt(1) + 2 * directionY),
                        "" + (char) (activePawnPosition.charAt(0) + 2 * directionX) + activePawnPosition.charAt(1)
                };
                straight = false;
            }
            outer:
            for (int i = 0; i < expectedOpponentPawns.length; i++) {
                String expectedOpponentPawn = expectedOpponentPawns[i];
                String twoSquaresForPawn = twoSquares[i];
                Collection<String> walls = state.getWalls();
                String[] blockingWalls = getBlockingWalls(expectedOpponentPawn, activePawnPosition);
                for (String blockingWall : blockingWalls) {
                    if (walls.contains(blockingWall)) {
                        continue outer;
                    }
                }
                boolean wallBehind = false;
                blockingWalls = getBlockingWalls(twoSquaresForPawn, expectedOpponentPawn);
                for (String blockingWall : blockingWalls) {
                    if (walls.contains(blockingWall)) {
                        wallBehind = true;
                        break;
                    }
                }
                if (straight) {
                    if (!wallBehind) {
                        if (otherPawns.contains(expectedOpponentPawn)) {
                            return true;
                        }
                    }
                } else {
                    if (wallBehind) {
                        blockingWalls = getBlockingWalls(expectedOpponentPawn, move.getPosition());
                        for (String blockingWall : blockingWalls) {
                            if (walls.contains(blockingWall)) {
                                continue outer;
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static String[] getBlockingWalls(String position, String pawnPosition) {
        if (distanceBetweenPositions(position, pawnPosition) != 1) {
            throw new IllegalArgumentException();
        }
        String[] blockingWalls;
        int directionX = position.charAt(0) - pawnPosition.charAt(0);
        int directionY = position.charAt(1) - pawnPosition.charAt(1);
        if (directionY == 1) {
            blockingWalls = new String[]{
                    pawnPosition + "h",
                    "" + (char) (pawnPosition.charAt(0) - 1) + pawnPosition.charAt(1) + "h"
            };
        } else if (directionY == -1) {
            blockingWalls = new String[]{
                    position + "h",
                    "" + (char) (position.charAt(0) - 1) + position.charAt(1) + "h"
            };
        } else if (directionX == 1) {
            blockingWalls = new String[]{
                    pawnPosition + "v",
                    "" + pawnPosition.charAt(0) + (char) (pawnPosition.charAt(1) - 1) + "v"
            };
        } else {
            blockingWalls = new String[]{
                    position + "v",
                    "" + position.charAt(0) + (char) (position.charAt(1) - 1) + "v"
            };
        }
        return blockingWalls;
    }

    private static int distanceBetweenPositions(String position1, String position2) {
        return Math.abs(position1.charAt(0) - position2.charAt(0))
                + Math.abs(position1.charAt(1) - position2.charAt(1));
    }

    private static boolean isWallMoveValid(KuridorGameState state, KuridorMove move) {
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
        List<String> newWalls = new ArrayList<String>(state.getWalls());
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

    private static boolean hasAccessToLine(KuridorGameState state, String pawnPosition, char lineId) {
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

    private static Set<String> getAllNeighbours(KuridorGameState state, Set<String> positions) {
        Set<String> neighbours = new HashSet<String>();
        char[] limits = {'9', '1', 'i', 'a'};
        int[] limitsIndices = {1, 1, 0, 0};
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (String position : positions) {
            for (int i = 0; i < limits.length; i++) {
                if (position.charAt(limitsIndices[i]) != limits[i]) {
                    boolean blocked = false;
                    String potentialNeighbour = "" + (char) (position.charAt(0) + directions[i][0]) + (char) (position.charAt(1) + directions[i][1]);
                    String[] blockingWalls = getBlockingWalls(position, potentialNeighbour);
                    for (String blockingWall : blockingWalls) {
                        if (state.getWalls().contains(blockingWall)) {
                            blocked = true;
                            break;
                        }
                    }
                    if (!blocked) {
                        neighbours.add(potentialNeighbour);
                    }
                }
            }
        }
        neighbours.removeAll(positions);
        return neighbours;
    }
}
