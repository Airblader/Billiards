package de.buerkingo.billiards.game.straight;

import java.io.Serializable;

import com.google.common.base.Optional;

import de.buerkingo.billiards.game.straight.foul.Foul;
import de.buerkingo.billiards.util.reject.Reject;

public class StraightPoolInning implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int number;

    private boolean hasEnded = false;
    private int points = 0;
    private boolean endedWithSafety = false;
    private Optional<Foul> foul = Optional.absent();

    public StraightPoolInning( int number ) {
        this.number = number;
    }

    public int getEffectivePoints() {
        return points - ( foul.isPresent() ? foul.get().getPointsToDeduct() : 0 );
    }

    public int getNumber() {
        return number;
    }

    public StraightPoolInning addPoints( int points ) {
        Reject.ifLessThan( points, 0 );

        this.points += points;
        return this;
    }

    public int getPoints() {
        return points;
    }

    public boolean endedWithSafety() {
        return endedWithSafety;
    }

    public Optional<Foul> getFoul() {
        return foul;
    }

    public boolean hasEnded() {
        return hasEnded;
    }

    public StraightPoolInning end() {
        hasEnded = true;
        return this;
    }

}