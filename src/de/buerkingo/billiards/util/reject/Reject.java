package de.buerkingo.billiards.util.reject;

import static java.lang.String.format;

public class Reject {

    public static void ifLessThan( int actual, int expected ) {
        ifLessThan( format( "expected %d to be less than %d", actual, expected ), actual, expected );
    }

    public static void ifLessThan( String description, int actual, int expected ) {
        if( actual < expected ) {
            reject( description );
        }
    }

    public static void ifGreaterThan( int actual, int expected ) {
        ifGreaterThan( format( "expected %d to be greater than %d", actual, expected ), actual, expected );
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