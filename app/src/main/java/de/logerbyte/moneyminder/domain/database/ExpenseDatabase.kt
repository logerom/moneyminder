package de.logerbyte.moneyminder.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import de.logerbyte.moneyminder.domain.database.expense.Expense
import de.logerbyte.moneyminder.domain.database.expense.ExpenseDao

/**
 * Created by logerom on 08.08.18.
 */
@Database(entities = [Expense::class], version = 3)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}