package de.buerkingo.billiards.util.reject;

public class Reject {

    public static void ifLessThan( int actual, int expected ) {
        ifLessThan( null, actual, expected );
    }

    public static void ifLessThan( String description, int actual, int expected ) {
        if( actual < expected ) {
            reject( description );
        }
    }

    public static void ifGreaterThan( int actual, int expected ) {
        ifGreaterThan( null, actual, expected );
    }

    public static void ifGreaterThan( String description, int actual, int expected ) {
        if( actual > expected ) {
            reject( description );
        }
    }

    private static void reject( String description ) {
        throw new AssumptionException( description );
    }

}
