package de.buerkingo.billiards.util.reject;

import static java.lang.String.format;

import java.util.List;

public class Reject {

    public static void ifNull( Object obj ) {
        ifNull( "expected parameter not to be null", obj );
    }

    public static void ifNull( String description, Object obj ) {
        if( obj == null ) {
            reject( description );
        }
    }

    public static void ifNotNull( Object obj ) {
        ifNotNull( "expected parameter to be null", obj );
    }

    public static void ifNotNull( String description, Object obj ) {
        if( obj != null ) {
            reject( description );
        }
    }

    public static void ifEqual( int actual, int expected ) {
        ifEqual( format( "expected %d not to be equal to %d", actual, expected ), actual, expected );
    }

    public static void ifEqual( String description, int actual, int expected ) {
        if( actual == expected ) {
            reject( description );
        }
    }

    public static void ifNotEqual( int actual, int expected ) {
        ifNotEqual( format( "expected %d to be equal to %d", actual, expected ), actual, expected );
    }

    public static void ifNotEqual( String description, int actual, int expected ) {
        if( actual != expected ) {
            reject( description );
        }
    }

    public static void ifLessThan( int actual, int expected ) {
        ifLessThan( format( "expected %d not to be less than %d", actual, expected ), actual, expected );
    }

    public static void ifLessThan( String description, int actual, int expected ) {
        if( actual < expected ) {
            reject( description );
        }
    }

    public static void ifGreaterThan( int actual, int expected ) {
        ifGreaterThan( format( "expected %d not to be greater than %d", actual, expected ), actual, expected );
    }

    public static void ifGreaterThan( String description, int actual, int expected ) {
        if( actual > expected ) {
            reject( description );
        }
    }

    public static void ifEmpty( List<?> list ) {
        ifEmpty( "expected not to be empty", list );
    }

    public static void ifEmpty( String description, List<?> list ) {
        ifNull( list );
        if( list.isEmpty() ) {
            reject( description );
        }
    }

    public static void ifNotEmpty( List<?> list ) {
        ifNotEmpty( "expected to be empty", list );
    }

    public static void ifNotEmpty( String description, List<?> list ) {
        ifNull( list );
        if( !list.isEmpty() ) {
            reject( description );
        }
    }

    private static void reject( String description ) {
        throw new AssumptionException( description );
    }

}