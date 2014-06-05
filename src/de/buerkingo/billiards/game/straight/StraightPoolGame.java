package de.buerkingo.billiards.game.straight;

import java.io.Serializable;

import de.buerkingo.billiards.game.Game;
import de.buerkingo.billiards.util.reject.Reject;

/**
 * Manages a game of straight pool.
 */
public class StraightPoolGame implements Game<StraightPoolEvent, StraightPoolRack>, Serializable {

    private static final long serialVersionUID = 1L;

    private final StraightPoolRack rack = new StraightPoolRack();

    @Override
    public void processEvent( StraightPoolEvent event ) {
        Reject.ifNull( event );

        // TODO
    }

    @Override
    public StraightPoolRack getRack() {
        return rack;
    }

}