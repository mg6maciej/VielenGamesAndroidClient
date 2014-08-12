package com.vielengames.data.kuridor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
final class LegalPawnMoveGeneratorImpl {

    private static final Pattern PAWN_MOVE_PATTERN = Pattern.compile("[a-i][1-9]");

    private final KuridorGameState state;
    private Set<KuridorMove> moves = new HashSet<KuridorMove>();

    public Collection<KuridorMove> getLegalPawnMoves() {
        moves.clear();
        for (KuridorMove.Direction direction : KuridorMove.Direction.values()) {
            addMoves(direction);
        }
        return Collections.unmodifiableSet(moves);
    }

    private void addMoves(KuridorMove.Direction direction) {
        String pawnPosition = state.getActiveTeamPawnPosition();
        String potentialMove = getMoveInDirection(pawnPosition, direction);
        if (isUnreachable(pawnPosition, potentialMove)) {
            return;
        }
        if (wouldJumpOnOpponent(potentialMove)) {
            addJumpMoves(direction, potentialMove);
        } else {
            addMove(potentialMove);
        }
    }

    private boolean wouldJumpOnOpponent(String potentialMove) {
        return state.getInactiveTeamsPawnPositions().contains(potentialMove);
    }

    private void addJumpMoves(KuridorMove.Direction direction, String potentialMove) {
        String potentialStraightJump = getMoveInDirection(potentialMove, direction);
        if (isUnreachable(potentialMove, potentialStraightJump)) {
            addSideJumpMoves(direction, potentialMove);
        } else {
            addMove(potentialStraightJump);
        }
    }

    private void addSideJumpMoves(KuridorMove.Direction direction, String potentialMove) {
        for (KuridorMove.Direction sideJumpDirection : direction.getSideJumpDirections()) {
            String potentialSideJump = getMoveInDirection(potentialMove, sideJumpDirection);
            if (isUnreachable(potentialMove, potentialSideJump)) {
                continue;
            }
            addMove(potentialSideJump);
        }
    }

    private void addMove(String move) {
        moves.add(KuridorMove.pawn(move));
    }

    private boolean isUnreachable(String pawnPosition, String newPawnPosition) {
        return isOutsideOfBoard(newPawnPosition) || MoveBlockedChecker.isBlockedByWall(state.getWalls(), pawnPosition, newPawnPosition);
    }

    private String getMoveInDirection(String pawnPosition, KuridorMove.Direction direction) {
        return direction.applyToPosition(pawnPosition);
    }

    private boolean isOutsideOfBoard(String pawnPosition) {
        return !PAWN_MOVE_PATTERN.matcher(pawnPosition).matches();
    }
}
