package de.logerbyte.moneyminder.util;

import java.util.ArrayList;
import java.util.List;

import de.logerbyte.moneyminder.cashsummary.CashAdapterItem;
import de.logerbyte.moneyminder.db.expense.Expense;

/**
 * Created by logerom on 09.08.18.
 */

public class ConvertUtil {

    public static ArrayList<CashAdapterItem> expensesToCashItems(List<Expense> expenses) {
        ArrayList<CashAdapterItem> cashAdapterItems = new ArrayList<>();

        for (Expense expense :expenses) {
            CashAdapterItem item = new CashAdapterItem(expense.id, expense.cashDate, expense.cashName, DigitUtil.dotToComma(String.valueOf(expense.cashInEuro)));
            cashAdapterItems.add(item);
        }
        return cashAdapterItems;
    }
}
