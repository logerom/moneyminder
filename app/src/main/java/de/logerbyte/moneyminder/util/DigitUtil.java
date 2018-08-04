package de.logerbyte.moneyminder.util;

/**
 * Created by logerom on 04.08.18.
 */

public class DigitUtil {
    public static String commaToDot(String commaString) {
        return commaString.replace(",", ".");
    }

    public static String dotToComma(String commaString) {
        return commaString.replace(".", ",");
    }
}