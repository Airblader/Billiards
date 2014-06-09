package de.buerkingo.billiards.game.straight.foul;

final public class ConsecutiveFoulsFoul implements Foul {

    @Override
    public int getPointsToDeduct() {
        return 15;
    }

    @Override
    public boolean requiresRerack() {
        return true;
    }

    @Override
    public FoulReason getReason() {
        return FoulReason.CONSECUTIVE_FOULS;
    }

}
