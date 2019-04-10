package de.logerbyte.moneyminder.util;

import java.util.List;

import de.logerbyte.moneyminder.db.expense.Expense;

/**
 * Created by logerom on 04.08.18.
 */

public class DigitUtil {

    public static String commaToDot(String commaString) {
        return commaString == null ? "" : commaString.replace(",", ".");
    }

    public static String dotToComma(String commaString) {
        return commaString.replace(".", ",");
    }

    public static String getCashTotal(List<Expense> cashList) {
        double totalCash = 0.0;

        for (Expense expense : cashList) {
            totalCash = totalCash + expense.cashInEuro;
        }
        return String.format("%.2f", totalCash);
    }
}