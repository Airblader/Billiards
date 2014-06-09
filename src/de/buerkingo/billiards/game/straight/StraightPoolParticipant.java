package de.buerkingo.billiards.game.straight;

import static com.google.common.collect.Lists.newArrayList;

import java.io.Serializable;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;

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

    public static final Ordering<StraightPoolParticipant> BY_POINTS = Ordering.natural().onResultOf( new Function<StraightPoolParticipant, Integer>() {
        @Override
        public Integer apply( StraightPoolParticipant participant ) {
            return participant.getPoints();
        }
    } );

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

    /** Guaranteed to return an open inning. If the last inning has ended, a new one will be appended and returned. */
    public StraightPoolInning getInningOrNew() {
        StraightPoolInning inning = getLastInning();
        if( inning.hasEnded() ) {
            innings.add( new StraightPoolInning( inning.getNumber() + 1 ) );
            return getInningOrNew();
        }

        return inning;
    }

    public StraightPoolInning getLastInning() {
        return Iterables.getLast( innings );
    }

    public StraightPoolInning getInning( int number ) {
        return Iterables.get( innings, number - 1, null );
    }

    public StraightPoolParticipant increaseConsecutiveFouls() {
        consecutiveFouls++;
        return this;
    }

    public StraightPoolParticipant resetConsecutiveFouls() {
        consecutiveFouls = 0;
        return this;
    }

    public int getPoints() {
        int points = 0;
        for( StraightPoolInning inning : innings ) {
            points += inning.getEffectivePoints();
        }

        return points;
    }

}