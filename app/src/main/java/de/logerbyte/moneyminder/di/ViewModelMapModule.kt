package de.logerbyte.moneyminder.di

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.CashAdapter
import de.logerbyte.moneyminder.ui.summaryList.CashSummaryViewModel
import de.logerbyte.moneyminder.viewModels.CashViewModel

@Module
class ViewModelMapModule {
    @Provides
    @IntoMap
    @ViewModelKey(CashSummaryViewModel::class)
    fun intoMapCashSummaryViewModel(cashAdapter: CashAdapter): ViewModel = CashSummaryViewModel(cashAdapter)

    @Provides
    @IntoMap
    @ViewModelKey(CashViewModel::class)
    fun provideCashSummaryViewModel(date: String): ViewModel = CashViewModel(date)
}