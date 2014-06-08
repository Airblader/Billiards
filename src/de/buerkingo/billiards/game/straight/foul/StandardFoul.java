package de.buerkingo.billiards.game.straight.foul;

import java.io.Serializable;

/**
 * A standard foul.
 */
final public class StandardFoul implements Foul, Serializable {

    private static final long serialVersionUID = 1L;

    private final FoulReason reason;

    public StandardFoul() {
        this( FoulReason.UNCATEGORIZED );
    }

    public StandardFoul( FoulReason reason ) {
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

    public FoulReason getReason() {
        return reason;
    }

}
