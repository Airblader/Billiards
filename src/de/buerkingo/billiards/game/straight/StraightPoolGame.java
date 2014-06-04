package de.buerkingo.billiards.game.straight;

import java.io.Serializable;

import de.buerkingo.billiards.game.Game;

/**
 * Manages a game of straight pool.
 */
public class StraightPoolGame implements Game<StraightPoolEvent, StraightPoolRack>, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public void processEvent( StraightPoolEvent event ) {
        // TODO
    }

}