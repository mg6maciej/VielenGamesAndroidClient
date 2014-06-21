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
            boolean straight = false;
            if (directionY == 2) {
                expectedOpponentPawns = new String[]{"" + activePawnPosition.charAt(0) + (char) (activePawnPosition.charAt(1) + 1)};
                straight = true;
            } else if (directionY == -2) {
                expectedOpponentPawns = new String[]{"" + activePawnPosition.charAt(0) + (char) (activePawnPosition.charAt(1) - 1)};
                straight = true;
            } else if (directionX == 2) {
                expectedOpponentPawns = new String[]{"" + (char) (activePawnPosition.charAt(0) + 1) + activePawnPosition.charAt(1)};
                straight = true;
            } else if (directionX == -2) {
                expectedOpponentPawns = new String[]{"" + (char) (activePawnPosition.charAt(0) - 1) + activePawnPosition.charAt(1)};
                straight = true;
            } else if (directionY == 1 && directionX == -1) {
                expectedOpponentPawns = new String[]{
                        "" + activePawnPosition.charAt(0) + (char) (activePawnPosition.charAt(1) + 1),
                        "" + (char) (activePawnPosition.charAt(0) - 1) + activePawnPosition.charAt(1)
                };
                twoSquares = new String[]{
                        "" + activePawnPosition.charAt(0) + (char) (activePawnPosition.charAt(1) + 2),
                        "" + (char) (activePawnPosition.charAt(0) - 2) + activePawnPosition.charAt(1)
                };
            } else if (directionY == 1 && directionX == 1) {
                expectedOpponentPawns = new String[]{
                        "" + activePawnPosition.charAt(0) + (char) (activePawnPosition.charAt(1) + 1),
                        "" + (char) (activePawnPosition.charAt(0) + 1) + activePawnPosition.charAt(1)
                };
                twoSquares = new String[]{
                        "" + activePawnPosition.charAt(0) + (char) (activePawnPosition.charAt(1) + 2),
                        "" + (char) (activePawnPosition.charAt(0) + 2) + activePawnPosition.charAt(1)
                };
            } else if (directionY == -1 && directionX == -1) {
                expectedOpponentPawns = new String[]{
                        "" + activePawnPosition.charAt(0) + (char) (activePawnPosition.charAt(1) - 1),
                        "" + (char) (activePawnPosition.charAt(0) - 1) + activePawnPosition.charAt(1)
                };
                twoSquares = new String[]{
                        "" + activePawnPosition.charAt(0) + (char) (activePawnPosition.charAt(1) - 2),
                        "" + (char) (activePawnPosition.charAt(0) - 2) + activePawnPosition.charAt(1)
                };
            } else if (directionY == -1 && directionX == 1) {
                expectedOpponentPawns = new String[]{
                        "" + activePawnPosition.charAt(0) + (char) (activePawnPosition.charAt(1) - 1),
                        "" + (char) (activePawnPosition.charAt(0) + 1) + activePawnPosition.charAt(1)
                };
                twoSquares = new String[]{
                        "" + activePawnPosition.charAt(0) + (char) (activePawnPosition.charAt(1) - 2),
                        "" + (char) (activePawnPosition.charAt(0) + 2) + activePawnPosition.charAt(1)
                };
            } else {
                throw new IllegalStateException();
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

}
