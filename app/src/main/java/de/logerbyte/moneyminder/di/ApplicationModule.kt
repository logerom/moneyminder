package de.logerbyte.moneyminder.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import de.logerbyte.moneyminder.db.ExpenseDatabase
import javax.inject.Singleton

@Module(includes = [ApplicationModuleBinds::class])
object ApplicationModule {
    @JvmStatic
    @Singleton
    @Provides
    fun provideDataBase(context: Context): ExpenseDatabase {
        return Room.databaseBuilder(
                context.applicationContext,
                ExpenseDatabase::class.java,
                "moneyminder.db"
        ).build()
    }
}

@Module
abstract class ApplicationModuleBinds {}
