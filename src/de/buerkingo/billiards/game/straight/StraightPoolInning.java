package de.buerkingo.billiards.game.straight;

import static com.google.common.collect.Lists.newArrayList;

import java.io.Serializable;
import java.util.List;

import de.buerkingo.billiards.game.straight.foul.Foul;
import de.buerkingo.billiards.util.reject.Reject;

public class StraightPoolInning implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int number;

    private boolean hasEnded = false;
    private int points = 0;
    private boolean endedWithSafety = false;
    private List<Foul> fouls = newArrayList();

    public StraightPoolInning( int number ) {
        this.number = number;
    }

    public int getEffectivePoints() {
        return points - getFoulPoints();
    }

    public int getNumber() {
        return number;
    }

    public StraightPoolInning addPoints( int points ) {
        Reject.ifLessThan( points, 0 );

        this.points += points;
        return this;
    }

    public boolean endedWithSafety() {
        return endedWithSafety;
    }

    public StraightPoolInning addFoul( Foul foul ) {
        Reject.ifNull( foul );

        fouls.add( foul );
        return this;
    }

    public int getFoulPoints() {
        int foulPoints = 0;
        for( Foul foul : fouls ) {
            foulPoints += foul.getPointsToDeduct();
        }

        return foulPoints;
    }

    public boolean hasEnded() {
        return hasEnded;
    }

    public StraightPoolInning end() {
        hasEnded = true;
        return this;
    }

}