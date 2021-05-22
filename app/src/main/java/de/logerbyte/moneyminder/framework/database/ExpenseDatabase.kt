package de.logerbyte.moneyminder.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by logerom on 08.08.18.
 */
@Database(entities = [Expense::class], version = 3)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}