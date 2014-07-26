package com.vielengames.data.kuridor;

import java.util.Collection;
import java.util.HashSet;

final class LegalPawnMoveGeneratorImpl {

    private static final int[][] SIMPLE_DIRECTIONS = {
            {-1, 0}, {0, -1}, {0, 1}, {1, 0}
    };
    private static final int[][][] LEFT_RIGHT_JUMP_DIRECTIONS = {
            {{0, -1}, {0, 1}}, {{-1, 0}, {1, 0}}, {{-1, 0}, {1, 0}}, {{0, -1}, {0, 1}}
    };

    public static Collection<KuridorMove> getLegalPawnMoves(KuridorGameState state) {
        Collection<KuridorMove> moves = new HashSet<KuridorMove>();
        String pawnPosition = state.getActiveTeamPawnPosition();
        for (int i = 0; i < SIMPLE_DIRECTIONS.length; i++) {
            int[] direction = SIMPLE_DIRECTIONS[i];
            String potentialMove = getMoveInDirection(pawnPosition, direction);
            if (isOutsideOfBoard(potentialMove) || isBlockedByWall(state, pawnPosition, potentialMove)) {
                continue;
            }
            if (state.getInactiveTeamsPawnPositions().contains(potentialMove)) {
                String potentialStraightJump = getMoveInDirection(potentialMove, direction);
                if (isOutsideOfBoard(potentialStraightJump)
                        || isBlockedByWall(state, potentialMove, potentialStraightJump)) {
                    for (int[] jumpDirection : LEFT_RIGHT_JUMP_DIRECTIONS[i]) {
                        String potentialSideJump = getMoveInDirection(potentialMove, jumpDirection);
                        if (isOutsideOfBoard(potentialSideJump)
                                || isBlockedByWall(state, potentialMove, potentialSideJump)) {
                            continue;
                        }
                        moves.add(KuridorMove.pawn(potentialSideJump));
                    }
                } else {
                    moves.add(KuridorMove.pawn(potentialStraightJump));
                }
            } else {
                moves.add(KuridorMove.pawn(potentialMove));
            }
        }
        return moves;
    }

    private static String getMoveInDirection(String pawnPosition, int[] direction) {
        char sideJumpFileLetter = (char) (pawnPosition.charAt(0) + direction[0]);
        char sideJumpRankLetter = (char) (pawnPosition.charAt(1) + direction[1]);
        return "" + sideJumpFileLetter + sideJumpRankLetter;
    }

    private static boolean isOutsideOfBoard(String pawnPosition) {
        char fileLetter = pawnPosition.charAt(0);
        char rankLetter = pawnPosition.charAt(1);
        return fileLetter < 'a' || fileLetter > 'i' || rankLetter < '1' || rankLetter > '9';
    }

    private static boolean isBlockedByWall(KuridorGameState state, String startPosition, String endPosition) {
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
