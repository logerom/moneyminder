package de.logerbyte.moneyminder.util;

import java.util.ArrayList;
import java.util.List;

import de.logerbyte.moneyminder.cashsummary.cashadapter.CashAdapterItemViewModel;
import de.logerbyte.moneyminder.db.expense.Expense;

/**
 * Created by logerom on 09.08.18.
 */

// TODO: 18.09.18 that are not an util
public class ConvertUtil {

    public static ArrayList<CashAdapterItemViewModel> expensesToCashItems(List<Expense> expenses) {
        ArrayList<CashAdapterItemViewModel> cashAdapterItemViewModels = new ArrayList<>();

        for (Expense expense :expenses) {
            CashAdapterItemViewModel item = new CashAdapterItemViewModel(expense.id, expense.cashDate, expense.cashName, DigitUtil.dotToComma(String.valueOf(expense.cashInEuro)));
            cashAdapterItemViewModels.add(item);
        }
        return cashAdapterItemViewModels;
    }
}
