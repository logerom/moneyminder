package de.logerbyte.moneyminder.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.logerbyte.moneyminder.ui.summaryList.CashSummaryActivity

@Module
abstract class ActivityInjectionModule {
    @ContributesAndroidInjector
    abstract fun contributesCashSummaryActivity(): CashSummaryActivity
}