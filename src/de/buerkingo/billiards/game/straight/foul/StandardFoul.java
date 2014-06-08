package de.buerkingo.billiards.game.straight.foul;

import java.io.Serializable;

import de.buerkingo.billiards.util.reject.Reject;

/**
 * A standard foul.
 */
public class StandardFoul implements Foul, Serializable {

    private static final long serialVersionUID = 1L;

    private final int pointsToDeduct;
    private final int pointsToDeductIfFirstShotAfterRerack;

    public StandardFoul() {
        this( 1, 2 );
    }

    public StandardFoul( int pointsToDeduct, int pointsToDeductIfFirstShotAfterRerack ) {
        this.pointsToDeduct = pointsToDeduct;
        this.pointsToDeductIfFirstShotAfterRerack = pointsToDeductIfFirstShotAfterRerack;
    }

    @Override
    public int getPointsToDeduct( FoulScenario scenario ) {
        switch( scenario ) {
            case DEFAULT:
                return pointsToDeduct;
            case FIRST_SHOT_AFTER_RERACK:
                return pointsToDeductIfFirstShotAfterRerack;
            default:
                throw Reject.always( "unknown scenario" );
        }
    }

    @Override
    public boolean requiresRerack() {
        return false;
    }

    @Override
    public boolean countsAsFoul() {
        return true;
    }

}
