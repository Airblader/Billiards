package de.buerkingo.billiards.game.straight;

import static com.google.common.collect.Lists.newArrayList;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.ImmutableList;

import de.buerkingo.billiards.game.GameState;
import de.buerkingo.billiards.util.reject.Reject;

public class StraightPoolState implements GameState, Serializable {

    private static final long serialVersionUID = 1L;

    private final List<StraightPoolParticipant> winners;

    public StraightPoolState() {
        this.winners = newArrayList();
    }

    public StraightPoolState( List<StraightPoolParticipant> winners ) {
        this.winners = ImmutableList.copyOf( winners );
    }

    /** Returns the winning participants in case the game is over. */
    public List<StraightPoolParticipant> getWinners() {
        Reject.ifFalse( "game is not over, cannot determine winners", isGameOver() );
        return winners;
    }

    /** Returns whether the game is over. */
    public boolean isGameOver() {
        return !winners.isEmpty();
    }

}