package de.logerbyte.moneyminder.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by logerom on 08.08.18.
 */
@Database(entities = [ExpenseEntity::class], version = 3)
abstract class Database : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}
