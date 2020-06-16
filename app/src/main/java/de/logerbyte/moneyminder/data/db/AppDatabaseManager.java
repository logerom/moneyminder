package de.logerbyte.moneyminder.data.db;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

import javax.inject.Inject;

import de.logerbyte.moneyminder.data.db.expense.Expense;
import io.reactivex.Observable;

/**
 * Created by logerom on 08.08.18.
 */


public class AppDatabaseManager implements DbHelper{

    private final ExpenseDatabase mExpenseDatabase;

    @Inject
    public AppDatabaseManager(ExpenseDatabase expenseDatabase) {
        this.mExpenseDatabase = expenseDatabase;
    }

    public AppDatabaseManager(Context context) {
        this.mExpenseDatabase = Room.databaseBuilder(context, ExpenseDatabase.class, "moneyminder.db")
                .addMigrations(DbMigration.MIGRATION_1_2)
                .build();
    }

    @Override
    public Observable<List<Expense>> getAllExpense() {
        return Observable.fromCallable(() -> mExpenseDatabase.expenseDao().selectAll());
    }

    @Override
    public Observable<Boolean> insertCashItemIntoDB(Expense expense) {
        return Observable.fromCallable(()-> {
            mExpenseDatabase.expenseDao().insert(expense);
            return true;
        });
    }

    @Override
    public Observable<Boolean> deleteCashItem(long id) {
        return Observable.fromCallable(() -> {
            mExpenseDatabase.expenseDao().delete(id);
            return true;
        });
    }

    @Override
    public Observable<Boolean> updateCashItem(Expense expense) {
        return Observable.fromCallable(() -> {
            mExpenseDatabase.expenseDao().update(expense);
            return true;
        });
    }

    @Override
    public Observable<List<String>> getCategories() {
        return Observable.fromCallable(() -> mExpenseDatabase.expenseDao().selectDistinctCategory());
    }
}
