package de.logerbyte.moneyminder.data.db.expense;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.logerbyte.moneyminder.data.db.ExpenseDatabase;
import io.reactivex.Observable;

/**
 * Created by logerom on 08.08.18.
 */

@Singleton
public class ExpenseRepo implements ExpenseAPI {

    private final ExpenseDatabase mExpenseDatabase;

    @Inject
    public ExpenseRepo(ExpenseDatabase expenseDatabase) {
        this.mExpenseDatabase = expenseDatabase;
    }

    @Override
    public Observable<List<Expense>> getAllExpense() {
        return Observable.fromCallable(() -> mExpenseDatabase.expenseDao().selectAll());
    }

    @Override
    public Observable<Boolean> insertCashItemIntoDB(Expense expense) {
        return Observable.fromCallable(() -> {
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
