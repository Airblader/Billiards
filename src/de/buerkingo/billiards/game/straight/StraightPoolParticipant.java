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
    private int points = 0;
    private int consecutiveFouls = 0;

    public StraightPoolParticipant( Identity identity ) {
        this.identity = identity;
    }

    @Override
    public Identity getIdentity() {
        return identity;
    }

    public int getPoints() {
        return points;
    }

    public StraightPoolParticipant addPoints( int points ) {
        this.points += points;
        return this;
    }

    public int getConsecutiveFouls() {
        return consecutiveFouls;
    }

    public StraightPoolParticipant increaseConsecutiveFouls() {
        consecutiveFouls++;
        return this;
    }

    public StraightPoolParticipant resetConsecutiveFouls() {
        consecutiveFouls = 0;
        return this;
    }

}