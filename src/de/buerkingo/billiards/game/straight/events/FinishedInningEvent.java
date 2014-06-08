package de.buerkingo.billiards.game.straight.events;

import java.io.Serializable;

import com.google.common.base.Optional;

import de.buerkingo.billiards.game.GameEvent;
import de.buerkingo.billiards.game.straight.foul.Foul;

/**
 * Event that should be sent when a player finishes his inning.
 */
public class FinishedInningEvent implements GameEvent, Serializable {

    private static final long serialVersionUID = 1L;

    private final int numberOfRemainingBalls;
    private boolean endedWithSafety = false;
    private Optional<Foul> foul = Optional.absent();

    public FinishedInningEvent( int numberOfRemainingBalls ) {
        this.numberOfRemainingBalls = numberOfRemainingBalls;
    }

    public FinishedInningEvent withSafety() {
        this.endedWithSafety = true;
        return this;
    }

    public FinishedInningEvent withFoul( Foul foul ) {
        this.foul = Optional.fromNullable( foul );
        return this;
    }

    public int getNumberOfRemainingBalls() {
        return numberOfRemainingBalls;
    }

    public boolean endedWithSafety() {
        return endedWithSafety;
    }

    public Optional<Foul> getFoul() {
        return foul;
    }

}