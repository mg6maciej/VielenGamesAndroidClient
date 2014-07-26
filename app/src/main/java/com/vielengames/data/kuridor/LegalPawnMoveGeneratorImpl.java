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
            char fileLetter = (char) (pawnPosition.charAt(0) + direction[0]);
            char rankLetter = (char) (pawnPosition.charAt(1) + direction[1]);
            if (isOutsideOfBoard(fileLetter, rankLetter)) {
                continue;
            }
            String potentialMove = "" + fileLetter + rankLetter;
            if (isBlockedByWall(state, pawnPosition, potentialMove)) {
                continue;
            }
            if (state.getInactiveTeamsPawnPositions().contains(potentialMove)) {
                char jumpFileLetter = (char) (potentialMove.charAt(0) + direction[0]);
                char jumpRankLetter = (char) (potentialMove.charAt(1) + direction[1]);
                String potentialStraightJump = "" + jumpFileLetter + jumpRankLetter;
                if (isOutsideOfBoard(jumpFileLetter, jumpRankLetter) || isBlockedByWall(state, potentialMove, potentialStraightJump)) {
                    for (int[] jumpDirection : LEFT_RIGHT_JUMP_DIRECTIONS[i]) {
                        char sideJumpFileLetter = (char) (potentialMove.charAt(0) + jumpDirection[0]);
                        char sideJumpRankLetter = (char) (potentialMove.charAt(1) + jumpDirection[1]);
                        String potentialSideJump = "" + sideJumpFileLetter + sideJumpRankLetter;
                        if (isBlockedByWall(state, potentialMove, potentialSideJump)) {
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

    private static boolean isOutsideOfBoard(char fileLetter, char rankLetter) {
        return fileLetter < 'a' || fileLetter > 'i' || rankLetter < '1' || rankLetter > '9';
    }

    private static boolean isBlockedByWall(KuridorGameState state, String pawnPosition, String potentialMove) {
        String[] blockingWalls = getBlockingWalls(pawnPosition, potentialMove);
        for (String blockingWall : blockingWalls) {
            if (state.getWalls().contains(blockingWall)) {
                return true;
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
