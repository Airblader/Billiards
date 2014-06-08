package de.buerkingo.billiards.game.straight.foul;

import java.io.Serializable;

public class BreakFoul implements Foul, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public int getPointsToDeduct() {
        return 2;
    }

    @Override
    public boolean requiresRerack() {
        // TODO
        return false;
    }

    @Override
    public boolean countsAsFoul() {
        return false;
    }

    @Override
    public FoulReason getReason() {
        return FoulReason.BREAK_FOUL;
    }

}
