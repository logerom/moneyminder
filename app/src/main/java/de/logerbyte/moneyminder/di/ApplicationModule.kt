package de.logerbyte.moneyminder.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import de.logerbyte.moneyminder.DB_NAME
import de.logerbyte.moneyminder.SHARED_PREF
import de.logerbyte.moneyminder.data.db.ExpenseDatabase
import javax.inject.Singleton

@Module(includes = [ApplicationModuleBinds::class])
object ApplicationModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideDataBase(context: Context): ExpenseDatabase {
        return Room.databaseBuilder(
                context.applicationContext,
                ExpenseDatabase::class.java, DB_NAME
        ).build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideSharedPref(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }
}

@Module
abstract class ApplicationModuleBinds {}
