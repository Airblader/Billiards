package de.buerkingo.billiards.participants;

/**
 * Represents an individual person.
 */
public class Person implements Identity {

    private final String name;

    public Person( String name ) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}