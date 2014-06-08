package de.buerkingo.billiards.game.straight.foul;

import java.io.Serializable;

import de.buerkingo.billiards.util.reject.Reject;

/**
 * A standard foul.
 */
// TODO maybe a foul category as defined here? http://www.wpa-pool.com/web/index.asp?id=119&pagetype=rules#4.3
public class StandardFoul implements Foul, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public int getPointsToDeduct( FoulScenario scenario ) {
        switch( scenario ) {
            case DEFAULT:
                return 1;
            case FIRST_SHOT_AFTER_RERACK:
                return 2;
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
