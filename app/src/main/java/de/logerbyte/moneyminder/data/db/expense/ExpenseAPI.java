package de.logerbyte.moneyminder.data.db.expense;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by logerom on 09.08.18.
 */

public interface ExpenseAPI {
    Observable<List<Expense>> getAllExpense();

    Observable<Boolean> insertCashItemIntoDB(Expense expense);

    Observable<Boolean> deleteCashItem(long id);

    Observable<Boolean> updateCashItem(Expense expense);

    Observable<List<String>> getCategories();

    Observable<List<Expense>> expensesWithCategories(@NotNull HashSet<String> checkedCategories);
}
