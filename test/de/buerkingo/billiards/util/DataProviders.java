package de.buerkingo.billiards.util;

import com.tngtech.java.junit.dataprovider.DataProvider;

public class DataProviders {

    @DataProvider
    public static Object[][] provideTrueFalse() {
        return new Object[][] {
            { true }, { false }
        };
    }

    @DataProvider
    public static Object[][] provideZeroOne() {
        return new Object[][] {
            { 0 }, { 1 }
        };
    }

    @DataProvider
    public static Object[][] provideZeroToFifteen() {
        return new Object[][] {
            { 0 }, { 1 }, { 2 }, { 3 }, { 4 }, { 5 }, { 6 }, { 7 }, { 8 }, { 9 }, { 10 }, { 11 }, { 12 }, { 13 }, { 14 }, { 15 }
        };
    }

    @DataProvider
    public static Object[][] provideTwoToFifteen() {
        return new Object[][] {
            { 2 }, { 3 }, { 4 }, { 5 }, { 6 }, { 7 }, { 8 }, { 9 }, { 10 }, { 11 }, { 12 }, { 13 }, { 14 }, { 15 }
        };
    }

}
