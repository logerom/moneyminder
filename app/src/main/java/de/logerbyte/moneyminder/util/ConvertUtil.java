package de.logerbyte.moneyminder.util;

import java.util.ArrayList;
import java.util.List;

import de.logerbyte.moneyminder.cashOverview.cashadapter.DayExpenseViewModel;
import de.logerbyte.moneyminder.domain.database.expense.Expense;

/**
 * Created by logerom on 09.08.18.
 */

// fixme: 18.09.18 that are not an util
public class ConvertUtil {

    public static ArrayList<DayExpenseViewModel> expensesToViewModelCashItems(List<Expense> expenses) {
        ArrayList<DayExpenseViewModel> dayExpenseViewModels = new ArrayList<>();

        for (Expense expense :expenses) {
            // TODO: 22.02.21 convert to kotlin and use data classes
            DayExpenseViewModel item = new DayExpenseViewModel(expense.id, expense.cashDate,
                    expense.cashName, expense.category, DigitUtil.INSTANCE.dotToComma(String.valueOf(expense.cashInEuro)));
            dayExpenseViewModels.add(item);
        }
        return dayExpenseViewModels;
    }
}
