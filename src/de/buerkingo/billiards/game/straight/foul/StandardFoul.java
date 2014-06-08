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
        this.pointsToDeduct = 1;
        this.pointsToDeductIfFirstShotAfterRerack = 2;
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
