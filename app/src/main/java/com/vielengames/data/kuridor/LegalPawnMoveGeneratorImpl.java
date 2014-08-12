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

    private static final int[] LEFT = {-1, 0};
    private static final int[] RIGHT = {1, 0};
    private static final int[] UP = {0, 1};
    private static final int[] DOWN = {0, -1};

    private static final int[][] MOVE_DIRECTIONS = {
            LEFT,
            RIGHT,
            UP,
            DOWN
    };
    private static final int[][][] SIDE_JUMP_DIRECTIONS = {
            {DOWN, UP},
            {DOWN, UP},
            {LEFT, RIGHT},
            {LEFT, RIGHT}
    };

    private final KuridorGameState state;
    private Set<KuridorMove> moves = new HashSet<KuridorMove>();

    public Collection<KuridorMove> getLegalPawnMoves() {
        moves.clear();
        for (int index = 0; index < MOVE_DIRECTIONS.length; index++) {
            addMoves(index);
        }
        return Collections.unmodifiableSet(moves);
    }

    private void addMoves(int index) {
        int[] direction = MOVE_DIRECTIONS[index];
        String pawnPosition = state.getActiveTeamPawnPosition();
        String potentialMove = getMoveInDirection(pawnPosition, direction);
        if (isUnreachable(pawnPosition, potentialMove)) {
            return;
        }
        if (wouldJumpOnOpponent(potentialMove)) {
            addJumpMoves(index, potentialMove);
        } else {
            addMove(potentialMove);
        }
    }

    private boolean wouldJumpOnOpponent(String potentialMove) {
        return state.getInactiveTeamsPawnPositions().contains(potentialMove);
    }

    private void addJumpMoves(int index, String potentialMove) {
        int[] direction = MOVE_DIRECTIONS[index];
        String potentialStraightJump = getMoveInDirection(potentialMove, direction);
        if (isUnreachable(potentialMove, potentialStraightJump)) {
            addSideJumpMoves(index, potentialMove);
        } else {
            addMove(potentialStraightJump);
        }
    }

    private void addSideJumpMoves(int index, String potentialMove) {
        for (int[] jumpDirection : SIDE_JUMP_DIRECTIONS[index]) {
            String potentialSideJump = getMoveInDirection(potentialMove, jumpDirection);
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
        return isOutsideOfBoard(newPawnPosition) || isBlockedByWall(pawnPosition, newPawnPosition);
    }

    private String getMoveInDirection(String pawnPosition, int[] direction) {
        char sideJumpFileLetter = (char) (pawnPosition.charAt(0) + direction[0]);
        char sideJumpRankLetter = (char) (pawnPosition.charAt(1) + direction[1]);
        return "" + sideJumpFileLetter + sideJumpRankLetter;
    }

    private boolean isOutsideOfBoard(String pawnPosition) {
        return !PAWN_MOVE_PATTERN.matcher(pawnPosition).matches();
    }

    private boolean isBlockedByWall(String startPosition, String endPosition) {
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

    private int distanceBetweenPositions(String position1, String position2) {
        return Math.abs(position1.charAt(0) - position2.charAt(0))
                + Math.abs(position1.charAt(1) - position2.charAt(1));
    }
}
