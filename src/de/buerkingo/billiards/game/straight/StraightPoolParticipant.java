package de.buerkingo.billiards.game.straight;

import java.io.Serializable;

import de.buerkingo.billiards.participants.Identity;
import de.buerkingo.billiards.participants.Participant;

/**
 * Represents a participant of a straight pool match.
 */
public class StraightPoolParticipant implements Participant, Serializable {

    private static final long serialVersionUID = 1L;

    private final Identity identity;

    public StraightPoolParticipant( Identity identity ) {
        this.identity = identity;
    }

    @Override
    public Identity getIdentity() {
        return identity;
    }

}