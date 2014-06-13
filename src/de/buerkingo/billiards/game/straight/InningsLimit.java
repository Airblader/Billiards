package de.buerkingo.billiards.game.straight;

import java.io.Serializable;

import com.google.common.base.Optional;

public class InningsLimit implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int maxInnings;
    private final Optional<Integer> extension;

    public InningsLimit( int maxInnings, Optional<Integer> extension ) {
        this.maxInnings = maxInnings;
        this.extension = extension;
    }

    public int getMaxInnings() {
        return maxInnings;
    }

    public Optional<Integer> getExtension() {
        return extension;
    }

}
