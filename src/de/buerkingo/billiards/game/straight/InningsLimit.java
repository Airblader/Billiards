package de.buerkingo.billiards.game.straight;

import java.io.Serializable;

public class InningsLimit implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int maxInnings;
    private final int extension;

    public InningsLimit( int maxInnings, int extension ) {
        this.maxInnings = maxInnings;
        this.extension = extension;
    }

    public int getMaxInnings() {
        return maxInnings;
    }

    public int getExtension() {
        return extension;
    }

}
