package de.logerbyte.moneyminder.data.db;

import java.util.List;

import de.logerbyte.moneyminder.data.db.expense.Expense;
import io.reactivex.Observable;

/**
 * Created by logerom on 09.08.18.
 */

public interface DbHelper {
    Observable<List<Expense>> getAllExpense();

    Observable<Boolean> insertCashItemIntoDB(Expense expense);

    Observable<Boolean> deleteCashItem(long id);

    Observable<Boolean> updateCashItem(Expense expense);

    Observable<List<String>> getCategories();
}