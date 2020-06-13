package de.logerbyte.moneyminder.util;

import java.util.ArrayList;
import java.util.List;

import de.logerbyte.moneyminder.db.expense.Expense;
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.DayExpenseViewModel;

/**
 * Created by logerom on 09.08.18.
 */

// fixme: 18.09.18 that are not an util
public class ConvertUtil {

    public static ArrayList<DayExpenseViewModel> expensesToCashItems(List<Expense> expenses) {
        ArrayList<DayExpenseViewModel> dayExpenseViewModels = new ArrayList<>();

        for (Expense expense :expenses) {
            DayExpenseViewModel item = new DayExpenseViewModel(expense.id, expense.cashDate,
                    expense.cashName, expense.category, DigitUtil.INSTANCE.dotToComma(String.valueOf(expense.cashInEuro)));
            dayExpenseViewModels.add(item);
        }
        return dayExpenseViewModels;
    }
}
