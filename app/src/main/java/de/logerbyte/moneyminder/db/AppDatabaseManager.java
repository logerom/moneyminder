package de.logerbyte.moneyminder.db;

import android.content.Context;

import java.util.List;

import androidx.room.Room;
import de.logerbyte.moneyminder.db.expense.Expense;
import io.reactivex.Observable;

/**
 * Created by logerom on 08.08.18.
 */


public class AppDatabaseManager implements DbHelper{

    private final AppDatabase mAppDatabase;

    public AppDatabaseManager(Context context) {
        this.mAppDatabase = Room.databaseBuilder(context, AppDatabase.class, "moneyminder.db")
                .addMigrations(DbMigration.MIGRATION_1_2)
                .build();
    }

    @Override
    public Observable<List<Expense>> getAllExpense() {
        return Observable.fromCallable(() -> mAppDatabase.expenseDao().selectAll());
    }

    @Override
    public Observable<Boolean> insertCashItemIntoDB(Expense expense) {
        return Observable.fromCallable(()-> {
            mAppDatabase.expenseDao().insert(expense);
            return true;
        });
    }

    @Override
    public Observable<Boolean> deleteCashItem(long id) {
        return Observable.fromCallable(() -> {
            mAppDatabase.expenseDao().delete(id);
            return true;
        });
    }

    @Override
    public Observable<Boolean> updateCashItem(Expense expense) {
        return Observable.fromCallable(() -> {
            mAppDatabase.expenseDao().update(expense);
            return true;
        });
    }
}
