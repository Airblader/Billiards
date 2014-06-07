package de.buerkingo.billiards.game.straight.foul;

import java.io.Serializable;

/**
 * A standard foul.
 */
public class SimpleFoul implements Foul, Serializable {

    private static final long serialVersionUID = 1L;

    private final int pointsToDeduct;

    public SimpleFoul() {
        this( 1 );
    }

    public SimpleFoul( int pointsToDeduct ) {
        this.pointsToDeduct = pointsToDeduct;
    }

    @Override
    public int getPointsToDeduct() {
        return pointsToDeduct;
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
