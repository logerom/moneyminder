package de.logerbyte.moneyminder.ui.summaryList

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CashSummaryModule {
    @ContributesAndroidInjector()
    abstract fun contributesCashSummaryActivity(): CashSummaryActivity
}