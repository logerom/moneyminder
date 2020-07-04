package de.logerbyte.moneyminder.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.logerbyte.moneyminder.ui.summaryList.CashSummaryActivity
import de.logerbyte.moneyminder.ui.summaryList.CashSummaryViewModel

@Module
abstract class CashSummaryActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    abstract fun contributesCashSummaryActivity(): CashSummaryActivity

    @Module
    class InjectViewModel {

        @Provides
        fun provideCashSummaryViewModel(
                factory: ViewModelProvider.Factory,
                cashSummaryActivity: CashSummaryActivity
        ) = ViewModelProviders.of(cashSummaryActivity, factory).get(CashSummaryViewModel::class.java)

        @Provides
        fun providesFragmentManager(cashSummaryActivity: CashSummaryActivity) = cashSummaryActivity.supportFragmentManager
    }
}