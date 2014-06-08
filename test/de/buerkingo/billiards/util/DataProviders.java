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

}
