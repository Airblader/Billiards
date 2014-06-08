package de.buerkingo.billiards.game.straight.events;

import java.io.Serializable;

import de.buerkingo.billiards.game.straight.foul.Foul;
import de.buerkingo.billiards.util.reject.Reject;

public class FoulEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Foul foul;

    public FoulEvent( Foul foul ) {
        Reject.ifNull( foul );

        this.foul = foul;
    }

    public Foul getFoul() {
        return foul;
    }

}