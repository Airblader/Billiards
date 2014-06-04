package de.buerkingo.billiards.participants;

import java.io.Serializable;

/**
 * Represents an individual person.
 */
public class Person implements Identity, Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;

    public Person( String name ) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}