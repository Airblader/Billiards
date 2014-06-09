package de.buerkingo.billiards.game.straight.foul;

import java.io.Serializable;

import de.buerkingo.billiards.util.reject.Reject;

public class SeriousFoul implements Foul, Serializable {

    private static final long serialVersionUID = 1L;

    private final int pointsToDeduct;
    private final FoulReason reason;

    public static SeriousFoul unsportsmanlikeConduct() {
        return new SeriousFoul( FoulReason.UNSPORTSMANLIKE_CONDUCT );
    }

    public SeriousFoul( FoulReason reason ) {
        this( 15, reason );
    }

    public SeriousFoul( int pointsToDeduct, FoulReason reason ) {
        Reject.ifTrue( "a serious foul cannot have a standard foul reason", reason.isStandardFoul() );

        this.pointsToDeduct = pointsToDeduct;
        this.reason = reason;
    }

    @Override
    public int getPointsToDeduct() {
        return pointsToDeduct;
    }

    @Override
    public boolean requiresRerack() {
        return true;
    }

    @Override
    public FoulReason getReason() {
        return reason;
    }

}