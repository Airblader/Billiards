package de.buerkingo.billiards.game.straight;

import java.io.Serializable;

import com.google.common.base.Optional;

import de.buerkingo.billiards.game.GameState;
import de.buerkingo.billiards.participants.Participant;
import de.buerkingo.billiards.util.reject.Reject;

public class StraightPoolState implements GameState, Serializable {

    private static final long serialVersionUID = 1L;

    private final Participant activeParticipant;
    private final boolean hasRerack;

    private final Optional<Participant> winner;

    public StraightPoolState( Participant activeParticipant, boolean hasRerack ) {
        this.activeParticipant = activeParticipant;
        this.hasRerack = hasRerack;

        this.winner = Optional.absent();
    }

    public StraightPoolState( Participant winner ) {
        this.winner = Optional.of( winner );

        this.activeParticipant = null;
        this.hasRerack = false;
    }

    /** Returns the participant which is active after the processed event. */
    public Participant getActiveParticipant() {
        return activeParticipant;
    }

    /** Returns the winning participant in case the game is over. */
    public Participant getWinner() {
        Reject.ifFalse( "game is not over, cannot determine winner", isGameOver() );
        return winner.get();
    }

    /** Determines whether the processed event requires a re-rack. */
    public boolean hasRerack() {
        return hasRerack;
    }

    /** Returns whether the game is over. */
    public boolean isGameOver() {
        return winner.isPresent();
    }

}