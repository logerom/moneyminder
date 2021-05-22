package de.logerbyte.moneyminder.domain.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import dagger.Module
import dagger.Provides
import de.logerbyte.moneyminder.DB_NAME
import de.logerbyte.moneyminder.NAMED_DATE
import de.logerbyte.moneyminder.SHARED_PREF
import de.logerbyte.moneyminder.framework.database.DbMigration
import de.logerbyte.moneyminder.framework.database.ExpenseDatabase
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

@Module
object ApplicationModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideViewModelFactory(viewModelMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return requireNotNull(viewModelMap[modelClass as Class<out ViewModel>]).get() as T
        }
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideDataBase(context: Context): ExpenseDatabase {
        return Room.databaseBuilder(
                context.applicationContext,
                ExpenseDatabase::class.java, DB_NAME)
            .addMigrations(DbMigration.MIGRATION_2_3)
            .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideSharedPref(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }

    @Singleton
    @JvmStatic
    @Provides
    fun provideSdf() = SimpleDateFormat("dd.MM.yy", Locale.getDefault())

    @JvmStatic
    @Provides
    @Named(NAMED_DATE)
    fun provideDate(sdf: SimpleDateFormat) = sdf.format(Calendar.getInstance(Locale.getDefault()).time)

}
