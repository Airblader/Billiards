package de.buerkingo.billiards.game.straight.foul;

import java.io.Serializable;

import de.buerkingo.billiards.util.reject.Reject;

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
        Reject.ifNull( reason );
        Reject.ifFalse( "specified reason is not suitable for a standard foul", reason.isStandardFoul() );

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
