package de.logerbyte.moneyminder.util;

import java.util.ArrayList;
import java.util.List;

import de.logerbyte.moneyminder.cashsummary.CashItem;
import de.logerbyte.moneyminder.db.expense.Expense;

/**
 * Created by logerom on 09.08.18.
 */

public class ConvertUtil {

    public static ArrayList<CashItem> expensesToCashItems(List<Expense> expenses) {
        ArrayList<CashItem> cashItems = new ArrayList<>();

        for (Expense expense :expenses) {
            CashItem item = new CashItem(expense.cashDate, expense.cashName, DigitUtil.dotToComma(String.valueOf(expense.cashInEuro)));
            cashItems.add(item);
        }
        return cashItems;
    }
}
