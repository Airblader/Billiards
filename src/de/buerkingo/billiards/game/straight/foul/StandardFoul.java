package de.buerkingo.billiards.game.straight.foul;

import java.io.Serializable;

/**
 * A standard foul.
 */
// TODO maybe a foul category as defined here? http://www.wpa-pool.com/web/index.asp?id=119&pagetype=rules#4.3
final public class StandardFoul implements Foul, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public int getPointsToDeduct( boolean isFirstShot ) {
        return isFirstShot ? 2 : 1;
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
