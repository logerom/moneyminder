package de.logerbyte.moneyminder.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.logerbyte.moneyminder.presentation.activityCashSummary.CashSummaryActivity
import de.logerbyte.moneyminder.presentation.activityCashSummary.CashSummaryViewModel
import javax.inject.Named

@Module
abstract class CashSummaryActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    abstract fun contributesCashSummaryActivity(): CashSummaryActivity

    @Module
    class InjectViewModel {

        @Provides
        @Named("ANDROID_VIEWMODEL")
        fun provideCashSummaryViewModel(
                factory: ViewModelProvider.Factory,
                cashSummaryActivity: CashSummaryActivity
        ) = ViewModelProviders.of(cashSummaryActivity, factory).get(CashSummaryViewModel::class.java)

        @Provides
        fun providesFragmentManager(cashSummaryActivity: CashSummaryActivity) = cashSummaryActivity.supportFragmentManager
    }
}