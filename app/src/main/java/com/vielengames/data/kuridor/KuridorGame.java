package com.vielengames.data.kuridor;

import com.vielengames.data.Game;
import com.vielengames.data.Player;

import java.util.ArrayList;
import java.util.List;

import hrisey.Parcelable;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Builder;

@Parcelable
@Value
@Builder
@EqualsAndHashCode(of = "id")
public final class KuridorGame implements Game, android.os.Parcelable {

    String id;
    Player winner;
    List<Player> players;
    List<KuridorMove> moves;
    KuridorGameState currentState;

    public Player getActivePlayer() {
        String activeTeam = currentState.getActiveTeam();
        if (activeTeam == null) {
            return null;
        }
        for (Player p : players) {
            if (p.getTeam().equals(activeTeam)) {
                return p;
            }
        }
        throw new IllegalStateException();
    }

    public Player getLoser() {
        if (winner == null) {
            return null;
        }
        for (Player potentialLoser : players) {
            if (!winner.equals(potentialLoser)) {
                return potentialLoser;
            }
        }
        throw new IllegalStateException();
    }

    public List<KuridorGameState> getStates() {
        List<KuridorGameState> states = new ArrayList<KuridorGameState>();
        states.add(KuridorGameState.initial());
        for (KuridorMove move : moves) {
            KuridorGameState previous = states.get(states.size() - 1);
            KuridorGameState next = previous.move(move);
            states.add(next);
        }
        return states;
    }

    public String getLastMoveStartPosition() {
        if (moves.size() == 0) {
            return null;
        }
        KuridorMove lastMove = moves.get(moves.size() - 1);
        if (lastMove.isWall()) {
            return lastMove.getPosition();
        }
        for (int i = moves.size() - 3; i >= 0; i -= 2) {
            lastMove = moves.get(i);
            if (lastMove.isPawn()) {
                return lastMove.getPosition();
            }
        }
        return moves.size() % 2 == 0 ? "e9" : "e1";
    }
}
