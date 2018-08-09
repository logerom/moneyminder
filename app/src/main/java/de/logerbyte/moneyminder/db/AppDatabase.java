package de.logerbyte.moneyminder.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import de.logerbyte.moneyminder.db.expense.Expense;
import de.logerbyte.moneyminder.db.expense.ExpenseDao;

/**
 * Created by logerom on 08.08.18.
 */


@Database(entities = Expense.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ExpenseDao expenseDao();
}
