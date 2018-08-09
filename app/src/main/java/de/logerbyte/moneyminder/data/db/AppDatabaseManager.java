package de.logerbyte.moneyminder.data.db;

import java.util.List;

import de.logerbyte.moneyminder.data.db.expense.Expense;
import io.reactivex.Observable;

/**
 * Created by logerom on 08.08.18.
 */


public class AppDatabaseManager implements DbHelper{

    private final AppDatabase mAppDatabase;

    public AppDatabaseManager(AppDatabase mAppDatabase) {
        this.mAppDatabase = mAppDatabase;
    }

    @Override
    public Observable<List<Expense>> getAllExpense() {
        return Observable.fromCallable(() -> mAppDatabase.expenseDao().selectAll());
    }
}
