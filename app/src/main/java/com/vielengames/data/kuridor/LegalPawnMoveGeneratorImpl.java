package com.vielengames.data.kuridor;

import java.util.Collection;
import java.util.HashSet;

final class LegalPawnMoveGeneratorImpl {

    private static final int[][] SIMPLE_DIRECTIONS = {
            {-1, 0}, {0, -1}, {0, 1}, {1, 0}
    };

    public static Collection<KuridorMove> getLegalPawnMoves(KuridorGameState state) {
        Collection<KuridorMove> moves = new HashSet<KuridorMove>();
        String pawnPosition = state.getActiveTeamPawnPosition();
        outer:
        for (int[] direction : SIMPLE_DIRECTIONS) {
            char fileLetter = (char) (pawnPosition.charAt(0) + direction[0]);
            char rankLetter = (char) (pawnPosition.charAt(1) + direction[1]);
            if (fileLetter < 'a' || fileLetter > 'i' || rankLetter < '1' || rankLetter > '9') {
                continue;
            }
            String potentialMove = "" + fileLetter + rankLetter;
            String[] blockingWalls = getBlockingWalls(pawnPosition, potentialMove);
            for (String blockingWall : blockingWalls) {
                if (state.getWalls().contains(blockingWall)) {
                    continue outer;
                }
            }
            if (state.getInactiveTeamsPawnPositions().contains(potentialMove)) {
                char jumpFileLetter = (char) (potentialMove.charAt(0) + direction[0]);
                char jumpRankLetter = (char) (potentialMove.charAt(1) + direction[1]);
                String potentialStraightJump = "" + jumpFileLetter + jumpRankLetter;
                moves.add(KuridorMove.pawn(potentialStraightJump));
            } else {
                moves.add(KuridorMove.pawn(potentialMove));
            }
        }
        return moves;
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
