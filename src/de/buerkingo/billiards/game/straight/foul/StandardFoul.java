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
        // TODO only allow certain reasons
        this.reason = reason;
    }

    @Override
    public int getPointsToDeduct() {
        return 1;
    }

    @Override
    public boolean requiresRerack() {
        return false;
    }

    @Override
    public boolean countsAsFoul() {
        return true;
    }

    @Override
    public FoulReason getReason() {
        return reason;
    }

}
