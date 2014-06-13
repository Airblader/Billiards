package de.buerkingo.billiards.game.straight;

import static com.google.common.collect.Lists.newArrayList;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.ImmutableList;

import de.buerkingo.billiards.game.GameState;
import de.buerkingo.billiards.util.reject.Reject;

public class StraightPoolState implements GameState, Serializable {

    private static final long serialVersionUID = 1L;

    private final StraightPoolParticipant activeParticipant;
    private final boolean hasRerack;

    private final List<StraightPoolParticipant> winners;

    public StraightPoolState( StraightPoolParticipant activeParticipant, boolean hasRerack ) {
        this.activeParticipant = activeParticipant;
        this.hasRerack = hasRerack;

        this.winners = newArrayList();
    }

    public StraightPoolState( List<StraightPoolParticipant> winners ) {
        this.winners = ImmutableList.copyOf( winners );

        this.activeParticipant = null;
        this.hasRerack = false;
    }

    /** Returns the participant which is active after the processed event. */
    public StraightPoolParticipant getActiveParticipant() {
        return activeParticipant;
    }

    /** Returns the winning participants in case the game is over. */
    public List<StraightPoolParticipant> getWinners() {
        Reject.ifFalse( "game is not over, cannot determine winner", isGameOver() );
        return winners;
    }

    /** Determines whether the processed event requires a re-rack. */
    public boolean hasRerack() {
        return hasRerack;
    }

    /** Returns whether the game is over. */
    public boolean isGameOver() {
        return !winners.isEmpty();
    }

}