package de.buerkingo.billiards.game.straight;

import static com.google.common.collect.Lists.newArrayList;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Iterables;

import de.buerkingo.billiards.participants.Identity;
import de.buerkingo.billiards.participants.Participant;

/**
 * Represents a participant of a straight pool match.
 */
public class StraightPoolParticipant implements Participant, Serializable {

    private static final long serialVersionUID = 1L;

    private final Identity identity;

    private final List<StraightPoolInning> innings = newArrayList( new StraightPoolInning( 1 ) );
    private int consecutiveFouls = 0;

    public StraightPoolParticipant( Identity identity ) {
        this.identity = identity;
    }

    @Override
    public Identity getIdentity() {
        return identity;
    }

    public int getConsecutiveFouls() {
        return consecutiveFouls;
    }

    public StraightPoolInning getInning() {
        return Iterables.getLast( innings );
    }

}