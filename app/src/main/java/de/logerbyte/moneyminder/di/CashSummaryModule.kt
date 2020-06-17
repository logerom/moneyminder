package de.logerbyte.moneyminder.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.CashAdapter
import de.logerbyte.moneyminder.ui.summaryList.CashSummaryActivity
import de.logerbyte.moneyminder.ui.summaryList.CashSummaryViewModel

@Module(includes = [CashSummaryModule.ProvideViewModel::class])
abstract class CashSummaryModule {

    // TODO-SW: Scoping dagger android subcomponents
    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    abstract fun contributesCashSummaryActivity(): CashSummaryActivity

    @Module
    class InjectViewModel {

        @Provides
        fun provideCashSummaryViewModel(
                factory: ViewModelProvider.Factory,
                cashSummaryActivity: CashSummaryActivity
        ) = ViewModelProviders.of(cashSummaryActivity, factory).get(CashSummaryViewModel::class.java)
    }

    @Module
    object ProvideViewModel {

        @JvmStatic
        @Provides
        @IntoMap
        @ViewModelKey(CashSummaryViewModel::class)
        fun provideCashSummaryViewModel(cashAdapter: CashAdapter): ViewModel = CashSummaryViewModel(cashAdapter)
    }
}