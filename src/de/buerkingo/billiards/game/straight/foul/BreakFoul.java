package de.buerkingo.billiards.game.straight.foul;

import java.io.Serializable;

public class BreakFoul implements Foul, Serializable {

    private static final long serialVersionUID = 1L;

    private final boolean withRerack;

    public BreakFoul( boolean withRerack ) {
        this.withRerack = withRerack;
    }

    @Override
    public int getPointsToDeduct() {
        return 2;
    }

    @Override
    public boolean requiresRerack() {
        return withRerack;
    }

    @Override
    public FoulReason getReason() {
        return FoulReason.BREAK_FOUL;
    }

}
