package de.buerkingo.billiards.game.straight;

import java.io.Serializable;

import de.buerkingo.billiards.game.GameState;
import de.buerkingo.billiards.participants.Participant;

public class StraightPoolState implements GameState, Serializable {

    private static final long serialVersionUID = 1L;

    private final Participant activeParticipant;
    private final boolean hasRerack;

    public StraightPoolState( Participant activeParticipant, boolean hasRerack ) {
        this.activeParticipant = activeParticipant;
        this.hasRerack = hasRerack;
    }

    /** Returns the participant which is active after the processed event. */
    public Participant getActiveParticipant() {
        return activeParticipant;
    }

    /** Determines whether the processed event requires a re-rack. */
    public boolean hasRerack() {
        return hasRerack;
    }

}