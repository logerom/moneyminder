package de.logerbyte.moneyminder.data.dbItem.expense;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

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

    Observable<List<Expense>> expensesWithCategories(@NotNull Set<String> checkedCategories);
}
