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
        for (int[] direction : SIMPLE_DIRECTIONS) {
            char fileLetter = (char) (pawnPosition.charAt(0) + direction[0]);
            char rankLetter = (char) (pawnPosition.charAt(1) + direction[1]);
            if (fileLetter < 'a' || fileLetter > 'i' || rankLetter < '1' || rankLetter > '9') {
                continue;
            }
            String potentialMove = "" + fileLetter + rankLetter;
            if (state.getInactiveTeamsPawnPositions().contains(potentialMove)) {
                continue;
            }
            moves.add(KuridorMove.pawn(potentialMove));
        }
        return moves;
    }
}
