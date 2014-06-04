package de.buerkingo.billiards.util.reject;

/** Indicates that something was rejected by {@link Reject}. */
public class AssumptionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AssumptionException( String message ) {
        super( message );
    }

}