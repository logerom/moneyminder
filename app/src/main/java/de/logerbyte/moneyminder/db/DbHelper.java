package de.logerbyte.moneyminder.db;

import java.util.List;

import de.logerbyte.moneyminder.db.expense.Expense;
import io.reactivex.Observable;

/**
 * Created by logerom on 09.08.18.
 */

public interface DbHelper {
    Observable<List<Expense>> getAllExpense();

    Observable<Boolean> insertCashItemIntoDB(Expense expense);

    Observable<Boolean> deleteCashItem(long id);
}
