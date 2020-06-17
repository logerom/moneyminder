package de.logerbyte.moneyminder.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import de.logerbyte.moneyminder.data.db.expense.Expense
import de.logerbyte.moneyminder.data.db.expense.ExpenseDao

/**
 * Created by logerom on 08.08.18.
 */
@Database(entities = [Expense::class], version = 2)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}