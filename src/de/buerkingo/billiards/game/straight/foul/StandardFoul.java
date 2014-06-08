package de.buerkingo.billiards.game.straight.foul;

import java.io.Serializable;

/**
 * A standard foul.
 */
final public class StandardFoul implements Foul, Serializable {

    private static final long serialVersionUID = 1L;

    private final StandardFoulReason reason;

    public StandardFoul() {
        this( StandardFoulReason.UNCATEGORIZED );
    }

    public StandardFoul( StandardFoulReason reason ) {
        this.reason = reason;
    }

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

    public StandardFoulReason getReason() {
        return reason;
    }

}
